package com.shuishu.demo.security.common.config.security.filter;


import com.shuishu.demo.security.common.config.security.handler.MyAuthenticationHandler;
import com.shuishu.demo.security.common.config.security.token.EmailAuthenticationToken;
import com.shuishu.demo.security.common.config.security.utils.SpringSecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

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
public class EmailLoginFilter extends AbstractAuthenticationProcessingFilter {

    public EmailLoginFilter(MyAuthenticationHandler myAuthenticationHandler) {
        // 登录路径，方式、认证管理器
        super(new AntPathRequestMatcher(SpringSecurityUtil.LOGIN_URL_EMAIL, RequestMethod.POST.name()));
        // 认证成功
        setAuthenticationSuccessHandler(myAuthenticationHandler);
        // 认证失败
        setAuthenticationFailureHandler(myAuthenticationHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String userAuthIdentifier = request.getParameter(SpringSecurityUtil.LOGIN_USERNAME_KEY);
        userAuthIdentifier = StringUtils.hasText(userAuthIdentifier) ? userAuthIdentifier.trim() : "";
        String userAuthCredential = request.getParameter(SpringSecurityUtil.LOGIN_PASSWORD_KEY);
        userAuthCredential = StringUtils.hasText(userAuthCredential) ? userAuthCredential.trim() : "";
        EmailAuthenticationToken emailAuthenticationToken = new EmailAuthenticationToken(userAuthIdentifier, userAuthCredential);
        emailAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
        return getAuthenticationManager().authenticate(emailAuthenticationToken);
    }

}
