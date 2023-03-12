package com.shuishu.demo.security.common.config.security;


import com.shuishu.demo.security.common.config.domain.ApiResponse;
import com.shuishu.demo.security.common.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;


/**
 * @author ：谁书-ss
 * @date ：2022-12-29 23:01
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p>
 * @description ：Handler
 * 设置Content-Type为application/json;charset=UTF-8
 * 根据情况设置状态码
 * 将返回结果写入到response
 * <p>
 * 接口：
 * AuthenticationSuccessHandler         ：认证成功 （自定登录接口，json的方式，实现此接口的方法是无效的）
 * AuthenticationFailureHandler         ：认证失败
 * LogoutSuccessHandler                 ：退出登录成功（自定注销登录接口，实现此接口的方法是无效的）
 * SessionInformationExpiredStrategy    ：会话过期 （token认证，实现此接口的方法是无效的）
 * AccessDeniedHandler                  ：验证权限失败 403 (用来解决认证过的用户访问无权限资源时的异常)
 * AuthenticationEntryPoint             ：匿名用户访问无权限资源时的异常，
 */
@Slf4j
@Component
public class GlobalAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler,
        LogoutSuccessHandler, SessionInformationExpiredStrategy,
        AccessDeniedHandler, AuthenticationEntryPoint {


    /**
     * 认证成功时的处理
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("登录成功：{}", authentication.getPrincipal());
        ResponseUtils.responseJson(response, ApiResponse.of(HttpStatus.OK.value(), "登录成功"));
    }

    /**
     * 认证失败时的处理
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     *                  request.
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
        log.warn("登录失败：{}", exception.getLocalizedMessage());
        ResponseUtils.responseJson(response, ApiResponse.of(HttpStatus.UNAUTHORIZED.value(), "登录失败"));
    }

    /**
     * 退出登录 成功处理
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 认证信息
     */

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("注销成功：{}", authentication.getPrincipal());
        ResponseUtils.responseJson(response, ApiResponse.of(ApiResponse.Type.UN_AUTH.value(), "注销成功"));
    }

    /**
     * 会话过期处理
     *
     * @param event 会话过期事件
     */
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) {
        log.warn("登录已过期，请重新登录");
        ResponseUtils.responseJson(event.getResponse(), ApiResponse.of(HttpStatus.UNAUTHORIZED.value(), "登录已过期，请重新登录"));
    }

    /**
     * 用来解决认证过的用户访问 无权限资源时的异常
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        log.warn("无权限访问：{}", accessDeniedException.getMessage());
        ResponseUtils.responseJson(response, ApiResponse.of(HttpStatus.FORBIDDEN.value(), "无权限访问"));
    }

    /**
     * 用来解决匿名（无认证）用户访问 无权限资源时的异常
     *
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        log.warn("请先登录，再访问资源：{}", authException.getMessage());
        ResponseUtils.responseJson(response, ApiResponse.of(HttpStatus.UNAUTHORIZED.value(), "请先登录，再访问资源"));
    }

}

