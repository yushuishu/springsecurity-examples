package com.shuishu.demo.security.common.config.security.config;


import com.shuishu.demo.security.common.config.security.provider.EmailAuthenticationProvider;
import com.shuishu.demo.security.common.config.security.provider.LocalDaoAuthenticationProvider;
import com.shuishu.demo.security.common.config.security.provider.PhoneAuthenticationProvider;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * @author ：谁书-ss
 * @date ：2023-03-06 21:02
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：AuthenticationManager 配置
 * <p></p>
 */
@Slf4j
@Configuration
public class AuthenticationManagerConfig {
    @Resource
    private EmailAuthenticationProvider emailAuthenticationProvider;
    @Resource
    private PhoneAuthenticationProvider phoneAuthenticationProvider;
    @Resource
    private LocalDaoAuthenticationProvider localDaoAuthenticationProvider;

    /**
     * 获取 AuthenticationManager（认证管理器），登录时认证使用
     *
     * @param httpSecurity 认证配置
     * @return 认证管理器
     * @throws Exception 异常
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        log.info("【AuthenticationManagerConfig】注册bean：authenticationManager");
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(emailAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(phoneAuthenticationProvider);
        authenticationManagerBuilder.authenticationProvider(localDaoAuthenticationProvider);
        return authenticationManagerBuilder.build();
    }
}
