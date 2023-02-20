/*
package com.shuishu.demo.springsecurity.common.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import javax.sql.DataSource;
import java.util.List;

*/
/**
 * @author ：谁书-ss
 * @date ：2022-12-29 21:59
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：SpringSecurity 配置
 *//*

@Configuration
public class MySecurityConfig {
    */
/**
     * 接口文档放行
     *//*

    public static final List<String> DOC_WHITE_LIST = List.of("/doc.html", "/webjars/**", "/v3/api-docs/**");
    */
/**
     * 验证码放行
     *//*

    public static final List<String> VERIFY_CODE_WHITE_LIST = List.of("/sys/verifyCode/**");



    */
/**
     * 获取 AuthenticationManager（认证管理器），登录时认证使用
     * 提供了 UsernamePasswordLoginFilter 需要的 AuthenticationManager
     *
     * @param authenticationConfiguration 认证配置
     * @return 认证管理器
     * @throws Exception 异常
     *//*

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    */
/**
     * 允许抛出用户不存在的异常
     * 提供了 MyRememberMeServices 需要的 PersistentTokenRepository
     * 其中 setCreateTableOnStartup()方法在首次运行的时候需要解开注释让它自动建表
     *
     * @param myUserDetailsService myUserDetailsService
     * @return DaoAuthenticationProvider
     *//*

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(MyUserDetailsServiceImpl myUserDetailsService) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setUserDetailsPasswordService(myUserDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    */
/**
     * 自定义RememberMe服务token持久化仓库
     *//*

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
                .requestMatchers(DOC_WHITE_LIST.toArray(new String[0])).permitAll()
                .requestMatchers(VERIFY_CODE_WHITE_LIST.toArray(new String[0])).permitAll()
                .requestMatchers("/api/shuishu/demo/user/login").permitAll()
                .anyRequest().authenticated();
        // 登录
        httpSecurity.addFilterAt(usernamePasswordLoginFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.formLogin().successHandler(myAuthenticationHandler);
        // 退出登录
        httpSecurity.logout().logoutUrl("/user/logout").logoutSuccessHandler(myAuthenticationHandler);
        // 禁用CSRF
        //httpSecurity.csrf().disable();
        // CSRF验证 存储到Cookie中
        httpSecurity.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler());
        //会话管理
        httpSecurity.sessionManagement()
                .maximumSessions(1)
                .expiredSessionStrategy(myAuthenticationHandler);
                // 引入redis-session依赖后已不再需要手动配置 sessionRegistry
                //.sessionRegistry(new SpringSessionBackedSessionRegistry<>(new RedisIndexedSessionRepository(RedisConfig.createRedisTemplate())))
                // 禁止后登陆挤下线
               //.maxSessionsPreventsLogin(true);
        // RememberMe
        httpSecurity.rememberMe().rememberMeServices(myRememberMeServices);
        // 权限不足时的处理
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(myAuthenticationHandler)
                .authenticationEntryPoint(myAuthenticationHandler);

        return httpSecurity.build();
    }

}
*/
