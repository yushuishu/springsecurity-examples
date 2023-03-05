package com.shuishu.demo.security.common.config.security.filter;


import com.shuishu.demo.security.common.config.security.handler.MyAuthenticationHandler;
import com.shuishu.demo.security.common.config.security.service.DbRememberMeServices;
import com.shuishu.demo.security.common.config.security.token.LocalAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.PhoneAuthenticationToken;
import com.shuishu.demo.security.common.config.security.utils.SpringSecurityUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;

/**
 * @author ：谁书-ss
 * @date ：2023-03-05 14:22
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：手机号登录过滤器
 * <p></p>
 */
@Component
public class PhoneLoginFilter extends AbstractAuthenticationProcessingFilter {
    protected PhoneLoginFilter(AuthenticationManager authenticationManager,
                               MyAuthenticationHandler myAuthenticationHandler,
                               DbRememberMeServices dbRememberMeServices) {
        // 登录路径，方式、认证管理器
        super(new AntPathRequestMatcher(SpringSecurityUtil.LOGIN_URL_PHONE, RequestMethod.POST.name()), authenticationManager);
        // 认证成功
        setAuthenticationSuccessHandler(myAuthenticationHandler);
        // 认证失败
        setAuthenticationFailureHandler(myAuthenticationHandler);
        // 记住我
        setRememberMeServices(dbRememberMeServices);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String userAuthIdentifier = request.getParameter(SpringSecurityUtil.LOGIN_USERNAME_KEY);
        userAuthIdentifier = StringUtils.hasText(userAuthIdentifier) ? userAuthIdentifier.trim() : "";
        String userAuthCredential = request.getParameter(SpringSecurityUtil.LOGIN_PASSWORD_KEY);
        userAuthCredential = StringUtils.hasText(userAuthCredential) ? userAuthCredential.trim() : "";
        PhoneAuthenticationToken phoneAuthenticationToken = new PhoneAuthenticationToken(userAuthIdentifier, userAuthCredential);
        phoneAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
        return getAuthenticationManager().authenticate(phoneAuthenticationToken);
    }

}
