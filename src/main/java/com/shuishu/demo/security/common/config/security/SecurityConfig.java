package com.shuishu.demo.security.common.config.security;


import com.shuishu.demo.security.common.config.security.filter.UsernamePasswordLoginFilter;
import com.shuishu.demo.security.common.config.security.handler.MyAuthenticationHandler;
import com.shuishu.demo.security.common.config.security.service.MyRememberMeServices;
import com.shuishu.demo.security.common.config.security.service.MyUserDetailsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.sql.DataSource;
import java.util.List;


/**
 * @author ：谁书-ss
 * @date ：2022-12-29 21:59
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：SpringSecurity 配置
 */

@Configuration
public class SecurityConfig {
    @Resource
    private IgnoreUrlConfig ignoreUrlConfig;

    /**
     * 获取 AuthenticationManager（认证管理器），登录时认证使用
     * 提供了 UsernamePasswordLoginFilter 需要的 AuthenticationManager
     *
     * @param authenticationConfiguration 认证配置
     * @return 认证管理器
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 允许抛出用户不存在的异常
     * 提供了 MyRememberMeServices 需要的 PersistentTokenRepository
     * 其中 setCreateTableOnStartup()方法在首次运行的时候需要解开注释让它自动建表
     *
     * @param myUserDetailsService myUserDetailsService
     * @return DaoAuthenticationProvider
    */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(MyUserDetailsServiceImpl myUserDetailsService) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setUserDetailsPasswordService(myUserDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
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
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   UsernamePasswordLoginFilter usernamePasswordLoginFilter,
                                                   MyAuthenticationHandler myAuthenticationHandler,
                                                   MyRememberMeServices myRememberMeServices) throws Exception{
        // 路径配置 （authorizeRequests 方法已废弃，取而代之的是 authorizeHttpRequests）
        // http.antMatcher()不再可用，并被替换为 http.securityMatcher() 或 httpSecurity.requestMatchers()
        httpSecurity.authorizeHttpRequests()
                .requestMatchers(ignoreUrlConfig.getUrls().toArray(new String[0])).permitAll()
                .anyRequest().authenticated();

        httpSecurity.formLogin()
                .disable()
                .usernameParameter("userAuthIdentifier")
                .passwordParameter("userAuthCredential")
                .loginProcessingUrl("/api/fyne/demo/auth/local")
                .successHandler(myAuthenticationHandler)
                .failureHandler(myAuthenticationHandler)
                .and()
                .logout()
                .logoutUrl("/api/fyne/demo/auth/logout")
                .logoutSuccessHandler(myAuthenticationHandler)
                .and()
                .rememberMe().rememberMeServices(myRememberMeServices)
                .and()
                .addFilterAt(usernamePasswordLoginFilter, UsernamePasswordAuthenticationFilter.class)
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
