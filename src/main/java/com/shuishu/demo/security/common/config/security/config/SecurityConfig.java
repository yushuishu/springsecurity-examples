package com.shuishu.demo.security.common.config.security.config;


import com.shuishu.demo.security.common.config.security.filter.EmailLoginFilter;
import com.shuishu.demo.security.common.config.security.filter.LocalLoginFilter;
import com.shuishu.demo.security.common.config.security.filter.PhoneLoginFilter;
import com.shuishu.demo.security.common.config.security.handler.MyAuthenticationHandler;
import com.shuishu.demo.security.common.config.security.provider.EmailAuthenticationProvider;
import com.shuishu.demo.security.common.config.security.provider.LocalDaoAuthenticationProvider;
import com.shuishu.demo.security.common.config.security.provider.PhoneAuthenticationProvider;
import com.shuishu.demo.security.common.config.security.service.DbRememberMeServices;
import com.shuishu.demo.security.common.config.security.utils.SpringSecurityUtil;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import javax.sql.DataSource;


/**
 * @author ：谁书-ss
 * @date ：2022-12-29 21:59
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：SpringSecurity 配置
 */

@Configuration
public class SecurityConfig {
    @Resource
    private EmailAuthenticationProvider emailAuthenticationProvider;
    @Resource
    private PhoneAuthenticationProvider phoneAuthenticationProvider;
    @Resource
    private LocalDaoAuthenticationProvider localDaoAuthenticationProvider;
    @Resource
    private LocalLoginFilter localLoginFilter;
    @Resource
    private EmailLoginFilter emailLoginFilter;
    @Resource
    private PhoneLoginFilter phoneLoginFilter;
    @Resource
    private DbRememberMeServices dbRememberMeServices;
    @Resource
    private IgnoreUrlConfig ignoreUrlConfig;

    /**
     * 获取 AuthenticationManager（认证管理器），登录时认证使用
     *
     * @param httpSecurity 认证配置
     * @return 认证管理器
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(emailAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(phoneAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(localDaoAuthenticationProvider.daoAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }

    /**
     * 自定义RememberMe服务token持久化仓库
     *
     * @param datasource datasource
     * @return -
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource datasource) {
        final JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        tokenRepository.setDataSource(datasource);
        //第一次启动的时候 自动建表
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   MyAuthenticationHandler myAuthenticationHandler) throws Exception{
        // 路径配置 （authorizeRequests 方法已废弃，取而代之的是 authorizeHttpRequests）
        // http.antMatcher()不再可用，并被替换为 http.securityMatcher() 或 httpSecurity.requestMatchers()
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(ignoreUrlConfig.getUrls().toArray(new String[0])).permitAll()
                .anyRequest().authenticated();

        httpSecurity.formLogin().disable()
                .addFilterBefore(localLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(emailLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(phoneLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl(SpringSecurityUtil.LOGOUT_URL)
                .logoutSuccessHandler(myAuthenticationHandler)
                .and()
                .rememberMe().rememberMeServices(dbRememberMeServices)
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                .and()
                .exceptionHandling()
                .accessDeniedHandler(myAuthenticationHandler)
                .authenticationEntryPoint(myAuthenticationHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.build();
    }

}
