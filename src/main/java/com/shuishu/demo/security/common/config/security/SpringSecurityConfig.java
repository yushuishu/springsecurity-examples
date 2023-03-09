package com.shuishu.demo.security.common.config.security;


import com.shuishu.demo.security.common.config.security.filter.EmailLoginFilter;
import com.shuishu.demo.security.common.config.security.filter.LocalLoginFilter;
import com.shuishu.demo.security.common.config.security.filter.PhoneLoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

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
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final MyAuthenticationHandler myAuthenticationHandler;
    private final AuthenticationManager authenticationManager;
    private final LocalLoginFilter localLoginFilter;
    private final EmailLoginFilter emailLoginFilter;
    private final PhoneLoginFilter phoneLoginFilter;



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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        // 路径配置 （authorizeRequests 方法已废弃，取而代之的是 authorizeHttpRequests）
        // http.antMatcher()不再可用，并被替换为 http.securityMatcher() 或 httpSecurity.requestMatchers()
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(ignoreUrlArray()).permitAll()
                .anyRequest().authenticated();

        httpSecurity
                .formLogin().disable()
                .addFilterBefore(localLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(emailLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(phoneLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl(SpringSecurityUtil.LOGOUT_URL)
                .logoutSuccessHandler(myAuthenticationHandler)
                //.and()
                //.rememberMe().rememberMeServices(dbRememberMeServices)
                .and()
                .csrf().disable()
                //.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                //.and()
                .exceptionHandling()
                .accessDeniedHandler(myAuthenticationHandler)
                .authenticationEntryPoint(myAuthenticationHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return httpSecurity.build();
    }

    private static String[] ignoreUrlArray(){
        return new String[]{
                "/api/shuishu/demo/doc.html",
                "/api/shuishu/demo/webjars/**",
                "/api/shuishu/demo/v3/api-docs/**",
                "/auth/**"
        };
    }
}
