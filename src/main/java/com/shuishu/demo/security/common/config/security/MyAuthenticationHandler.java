/*
package com.shuishu.demo.springsecurity.common.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuishu.demo.springsecurity.common.config.domain.ApiResponse;
import com.shuishu.demo.springsecurity.entity.po.User;
import com.shuishu.demo.springsecurity.entity.vo.UserInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import java.io.IOException;

*/
/**
 * @author ：谁书-ss
 * @date ：2022-12-29 23:01
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p>
 * @Description ：Handler
 * 设置Content-Type为application/json;charset=UTF-8
 * 根据情况设置状态码
 * 将返回结果写入到response
 * <p>
 * 接口：
 * AuthenticationSuccessHandler         ：认证成功
 * AuthenticationFailureHandler         ：认证失败
 * LogoutSuccessHandler                 ：退出登录成功
 * SessionInformationExpiredStrategy    ：会话过期
 * AccessDeniedHandler                  ：验证权限失败 403 (用来解决认证过的用户访问无权限资源时的异常)
 * AuthenticationEntryPoint             ：认证失败处理 (用来解决匿名用户访问无权限资源时的异常)
 *//*

@Component
public class MyAuthenticationHandler implements AuthenticationSuccessHandler,
        AuthenticationFailureHandler,
        LogoutSuccessHandler,
        SessionInformationExpiredStrategy,
        AccessDeniedHandler,
        AuthenticationEntryPoint {

    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    */
/**
     * 认证失败处理
     *
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException      异常
     *//*

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String detailMessage = authException.getClass().getSimpleName() + " " + authException.getLocalizedMessage();
        if (authException instanceof InsufficientAuthenticationException) {
            detailMessage = "请登陆后再访问";
        }
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(ApiResponse.of(detailMessage, ApiResponse.Type.ERROR.value(), "认证异常")));
    }


    */
/**
     * 权限不足时的处理
     *
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException getWriter 异常
     *//*

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        String detailMessage = null;
        if (accessDeniedException instanceof MissingCsrfTokenException) {
            detailMessage = "缺少CSRF TOKEN,请从表单或HEADER传入";
        } else if (accessDeniedException instanceof InvalidCsrfTokenException) {
            detailMessage = "无效的CSRF TOKEN";
        } else if (accessDeniedException instanceof CsrfException) {
            detailMessage = accessDeniedException.getLocalizedMessage();
        } else if (accessDeniedException instanceof AuthorizationServiceException) {
            detailMessage = AuthorizationServiceException.class.getSimpleName() + " " + accessDeniedException.getLocalizedMessage();
        }
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(ApiResponse.of(detailMessage, ApiResponse.Type.FORBIDDEN.value(), "禁止访问")));
    }


    */
/**
     * 认证失败时的处理
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     *                  request.
     * @throws IOException getWriter 异常
     *//*

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(ApiResponse.of(exception.getLocalizedMessage(), ApiResponse.Type.UN_AUTH.value(), "登录失败")));
    }


    */
/**
     * 认证成功时的处理
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     * @throws IOException getWriter 异常
     *//*

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setStatus(HttpStatus.OK.value());
        // TODO: 2023-01-01 用户实体
        //response.getWriter().println(OBJECT_MAPPER.writeValueAsString(ApiResponse.of(UserInfoVO.of(authentication), ApiResponse.Type.SUCCESS.value(), "登录成功")));
        //清理使用过的验证码
        //request.getSession().removeAttribute(VERIFY_CODE_KEY);
    }


    */
/**
     * 会话过期处理
     *
     * @param event 会话过期事件
     * @throws IOException getWriter 异常
     *//*

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        String message = "该账号已从其他设备登陆,如果不是您本人的操作请及时修改密码";
        final HttpServletResponse response = event.getResponse();
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(OBJECT_MAPPER.writeValueAsString(ApiResponse.of(event.getSessionInformation(), ApiResponse.Type.UN_AUTH.value(), message)));
    }

    */
/**
     * 退出登录 成功处理
     *
     * @param request        请求
     * @param response       响应
     * @param authentication 认证信息
     * @throws IOException   getWriter 异常
     *//*

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType(APPLICATION_JSON_CHARSET_UTF_8);
        response.setStatus(HttpStatus.OK.value());
        // TODO: 2023-01-01 用户实体
        //response.getWriter().println(OBJECT_MAPPER.writeValueAsString(ApiResponse.of(User.of(authentication), ApiResponse.Type.UN_AUTH.value(), "注销成功")));
    }

}
*/
