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
        if (!StringUtils.hasText(authToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        // Redis 获取token 的 用户信息，过期时间

        filterChain.doFilter(request, response);
    }


}
