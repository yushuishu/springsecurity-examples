package com.shuishu.demo.security.common.config.security.filter;


import com.shuishu.demo.security.common.config.domain.ApiResponse;
import com.shuishu.demo.security.common.config.security.SpringSecurityUtil;
import com.shuishu.demo.security.common.config.security.token.LocalAuthenticationToken;
import com.shuishu.demo.security.common.utils.ResponseUtils;
import com.shuishu.demo.security.common.utils.TokenUtils;
import com.shuishu.demo.security.entity.vo.UserInfoVo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;


/**
 * @author ：谁书-ss
 * @date ：2022-12-29 22:11
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：======================================== 本地账号登录过滤器 ============================================
 * AbstractAuthenticationProcessingFilter：是处理 form 登陆的过滤器
 * 1、继承了 AbstractAuthenticationProcessingFilter，实现类中的抽象方法 attemptAuthentication()
 * 2、重写构造方法，因为security中的 AbstractAuthenticationProcessingFilter类没有无参构造方法
 *    所以子类需要通过 super()方法显式的调用父类构造方法。
 *    构造方法的参数都可以从容器获取，所以这里也直接注册到容器自动构造
 * 3、构造函数中，指定了：
 *     拦截的登录请求和请求方式
 *     自定义认证管理器
 *     认证成功和失败时的处理器
 *     登陆使用的路径
 *     记住我功能的组件
 * 4、attemptAuthentication方法中规定了登陆流程：
 *     判断请求类型是否为 json ：如果Content-Type是Json，则从Body中获取请求参数，否则从Form表单中获取
 *     从请求体中获取登录参数   ：从Session的Attribute中获取之前保存的验证码，和用户提供的验证码进行比对
 *     ”记住我“功能参数       ： 把用户提供的rememberMe字段放到request的Attribute中，供后续 MyRememberMeService获取
 *     调用认证管理器的 authenticate()方法
 *
 * OncePerRequestFilter：保证一次外部请求，只执行一次过滤方法，对于服务器内部之间的forward等请求，不会再次执行过滤方法
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class LocalLoginFilter extends OncePerRequestFilter {

    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("【LocalLoginFilter 过滤器】执行doFilterInternal()方法");
        ContentCachingRequestWrapper contentCachingRequestWrapper = new ContentCachingRequestWrapper(request);
        // 获取URI
        String requestUri = request.getRequestURI();
        if (SpringSecurityUtil.LOGIN_URL_LOCAL.contains(requestUri) && !HttpMethod.POST.matches(request.getMethod())){
            ResponseUtils.responseJson(response,new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "不支持的请求方式"));
            return;
        }
        // 获取用户信息
        UserInfoVo userInfoVo = tokenUtils.getUserInfoVo(request, response);
        // 忽略URL
        if (SpringSecurityUtil.existsInIgnoreUrlArray(requestUri)){
            filterChain.doFilter(request, response);
            return;
        }
        // 需要认证的URL，token过期无效
        if (userInfoVo == null){
            ResponseUtils.responseJson(response,new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "请先登录，再访问资源"));
            return;
        }
        // 用户信息放到上下文
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            LocalAuthenticationToken localAuthenticationToken = new LocalAuthenticationToken(userInfoVo.getUserAuthIdentifier(), null, userInfoVo.getAuthorities());
            localAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(localAuthenticationToken);
        }
        filterChain.doFilter(request, response);
    }

}

