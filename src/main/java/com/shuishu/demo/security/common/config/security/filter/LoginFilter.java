package com.shuishu.demo.security.common.config.security.filter;


import com.shuishu.demo.security.common.config.domain.ApiResponse;
import com.shuishu.demo.security.common.config.security.SpringSecurityUtils;
import com.shuishu.demo.security.common.config.security.token.EmailAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.LocalAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.PhoneAuthenticationToken;
import com.shuishu.demo.security.common.utils.ResponseUtils;
import com.shuishu.demo.security.common.utils.TokenUtils;
import com.shuishu.demo.security.entity.dto.PermissionCacheDto;
import com.shuishu.demo.security.entity.vo.UserInfoVo;
import com.shuishu.demo.security.service.ResourceService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-03-12 11:38
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：登录认证 Filter
 * <p></p>
 * AbstractAuthenticationProcessingFilter：是处理 form 登陆的过滤器
 * 1、继承了 AbstractAuthenticationProcessingFilter，实现类中的抽象方法 attemptAuthentication()
 * 2、重写构造方法，因为security中的 AbstractAuthenticationProcessingFilter类没有无参构造方法
 * 所以子类需要通过 super()方法显式的调用父类构造方法。
 * 构造方法的参数都可以从容器获取，所以这里也直接注册到容器自动构造
 * 3、构造函数中，指定了：
 * 拦截的登录请求和请求方式
 * 自定义认证管理器
 * 认证成功和失败时的处理器
 * 登陆使用的路径
 * 记住我功能的组件
 * 4、attemptAuthentication方法中规定了登陆流程：
 * 判断请求类型是否为 json ：如果Content-Type是Json，则从Body中获取请求参数，否则从Form表单中获取
 * 从请求体中获取登录参数   ：从Session的Attribute中获取之前保存的验证码，和用户提供的验证码进行比对
 * ”记住我“功能参数       ： 把用户提供的rememberMe字段放到request的Attribute中，供后续 MyRememberMeService获取
 * 调用认证管理器的 authenticate()方法
 * <p>
 * OncePerRequestFilter：保证一次外部请求，只执行一次过滤方法，对于服务器内部之间的forward等请求，不会再次执行过滤方法
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LoginFilter extends OncePerRequestFilter {
    private final TokenUtils tokenUtils;
    private final ResourceService resourceService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("【LoginFilter 过滤器】执行doFilterInternal()方法");
        // 获取URI
        String requestUri = request.getRequestURI();
        if (SpringSecurityUtils.LOGIN_URL_LOCAL.contains(requestUri) ||
                SpringSecurityUtils.LOGIN_URL_EMAIL.contains(requestUri) ||
                SpringSecurityUtils.LOGIN_URL_PHONE.contains(requestUri)) {
            if (!HttpMethod.POST.matches(request.getMethod())) {
                ResponseUtils.responseJson(response, new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "不支持的请求方式"));
                return;
            }
        }
        // 获取用户信息
        UserInfoVo userInfoVo = tokenUtils.getUserInfoVo(request, response);

        // 忽略URL
        List<PermissionCacheDto> cachePermissionList = resourceService.findCachePermissionList();
        List<String> isNotAuthorizationUrlList = cachePermissionList.stream()
                .filter(t -> !Boolean.TRUE.equals(t.getIsNeedAuthorization()))
                .map(PermissionCacheDto::getPermissionUrl)
                .toList();
        // SpringSecurityUtils.ignoreUrlArray() 可以只配置注册登录相关页面，其它所有权限放到数据库，
        // 通过 dynamicAuthorizationManager 动态权限决策管理器，来动态管理
        if (SpringSecurityUtils.existsInIgnoreUrlArray(requestUri) || SpringSecurityUtils.existsInIgnoreUrlArrayForDb(requestUri, isNotAuthorizationUrlList)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 需要认证的URL，token过期无效
        if (userInfoVo == null) {
            ResponseUtils.responseJson(response, new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "请先登录，再访问资源"));
            return;
        }
        // 用户信息放到上下文
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            //不同的登录类型 可以分开处理。可以只创建一个Filter：LoginFilter，根据不同的登录url，来创建不同的 Token
            if (SpringSecurityUtils.LOGIN_URL_LOCAL.contains(requestUri)) {
                LocalAuthenticationToken localAuthenticationToken = new LocalAuthenticationToken(userInfoVo.getUserAuthIdentifier(), null, userInfoVo.getAuthorities());
                localAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(localAuthenticationToken);
            } else if (SpringSecurityUtils.LOGIN_URL_EMAIL.contains(requestUri)) {
                EmailAuthenticationToken emailAuthenticationToken = new EmailAuthenticationToken(userInfoVo.getUserAuthIdentifier(), userInfoVo.getAuthorities());
                emailAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(emailAuthenticationToken);
            } else if (SpringSecurityUtils.LOGIN_URL_PHONE.contains(requestUri)) {
                PhoneAuthenticationToken phoneAuthenticationToken = new PhoneAuthenticationToken(userInfoVo.getUserAuthIdentifier(), userInfoVo.getAuthorities());
                phoneAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(phoneAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
