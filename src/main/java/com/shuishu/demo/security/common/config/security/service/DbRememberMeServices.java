package com.shuishu.demo.security.common.config.security.service;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


/**
 * @author ：谁书-ss
 * @date ：2022-12-31 11:00
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：记住我
 * 1、从request的Attribute中获取rememberMe字段
 * 2、当字段值为TRUE_VALUES表的成员时认为需要开启记住我功能
 */
@Component
public class DbRememberMeServices extends PersistentTokenBasedRememberMeServices {
    public static final String REMEMBER_ME_KEY = "rememberMe";
    public static final List<String> TRUE_VALUES = List.of("true", "yes", "on", "1");

    public DbRememberMeServices(UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(UUID.randomUUID().toString(), userDetailsService, tokenRepository);
    }


    /**
     * 自定义获取 remember-me 方式
     *
     * @param request   the request submitted from an interactive login, which may include
     *                  additional information indicating that a persistent login is desired.
     * @param parameter the configured remember-me parameter name.
     * @return -
     */
    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        final String rememberMe = (String) request.getAttribute(REMEMBER_ME_KEY);
        if (rememberMe != null) {
            for (String trueValue : TRUE_VALUES) {
                if (trueValue.equalsIgnoreCase(rememberMe)) {
                    return true;
                }
            }
        }
        return super.rememberMeRequested(request, parameter);
    }
}
