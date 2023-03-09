package com.shuishu.demo.security.common.config.security.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


/**
 * @author ：谁书-ss
 * @date ：2023-03-05 14:24
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：QQ邮箱登录过滤器
 * <p></p>
 */
@Slf4j
@Component
public class EmailLoginFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        log.info("【EmailLoginFilter 过滤器】执行doFilterInternal()方法");
        // 获取URI
        String requestURI = request.getRequestURI();
        // 获取token
        final String authToken = request.getHeader("token");
        if (!StringUtils.hasText(authToken)){
            filterChain.doFilter(request, response);
            return;
        }
        // Redis 获取token 的 用户信息，过期时间

        filterChain.doFilter(request, response);
    }

    //public EmailLoginFilter() {
    //    // 登录路径，方式、认证管理器
    //    super(new AntPathRequestMatcher(SpringSecurityUtil.LOGIN_URL_EMAIL, RequestMethod.POST.name()));
    //    log.info("【EmailLoginFilter 过滤器】执行EmailLoginFilter()方法");
    //    // 认证成功
    //    //setAuthenticationSuccessHandler(myAuthenticationHandler);
    //    //// 认证失败
    //    //setAuthenticationFailureHandler(myAuthenticationHandler);
    //}

    //@Override
    //public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    //    log.info("【EmailLoginFilter 过滤器】执行attemptAuthentication()方法");
    //    String userAuthIdentifier = request.getParameter(SpringSecurityUtil.LOGIN_USERNAME_KEY);
    //    userAuthIdentifier = StringUtils.hasText(userAuthIdentifier) ? userAuthIdentifier.trim() : "";
    //    String userAuthCredential = request.getParameter(SpringSecurityUtil.LOGIN_PASSWORD_KEY);
    //    userAuthCredential = StringUtils.hasText(userAuthCredential) ? userAuthCredential.trim() : "";
    //    EmailAuthenticationToken emailAuthenticationToken = new EmailAuthenticationToken(userAuthIdentifier, userAuthCredential);
    //    emailAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
    //    return getAuthenticationManager().authenticate(emailAuthenticationToken);
    //}

}
