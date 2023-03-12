package com.shuishu.demo.security.common.config.security;


import com.shuishu.demo.security.common.config.security.token.EmailAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.LocalAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.PhoneAuthenticationToken;
import com.shuishu.demo.security.entity.vo.UserInfoVo;
import com.shuishu.demo.security.enums.UserEnum;
import com.shuishu.demo.security.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ：谁书-ss
 * @date ：2023-03-08 23:33
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：SpringSecurity 登录相关配置
 * <p></p>
 */
@Slf4j
@Configuration
@Component
@RequiredArgsConstructor
public class AuthenticationConfig {
    private final UserService userService;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        log.info("【AuthenticationManagerConfig】注册bean：authenticationManager");
        //AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        //AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        //authenticationManager.authenticationProvider(localDaoAuthenticationProvider());
        //authenticationManager.authenticationProvider(emailAuthenticationProvider());
        //authenticationManager.authenticationProvider(phoneAuthenticationProvider());
        return new ProviderManager(localDaoAuthenticationProvider(), emailAuthenticationProvider(), phoneAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider localDaoAuthenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                // 获取 账号、密码
                String userAuthIdentifier = (String) authentication.getPrincipal();
                String userAuthCredential = (String) authentication.getCredentials();
                log.info("【LocalDaoAuthenticationProvider 认证】执行authenticate()方法，获取账号：" + userAuthIdentifier);
                log.info("【LocalDaoAuthenticationProvider 认证】执行authenticate()方法，获取密码：" + userAuthCredential);

                // 获取账号用户信息
                UserInfoVo userInfoVO = (UserInfoVo) localUserDetailsService().loadUserByUsername(userAuthIdentifier);
                log.info("【LocalDaoAuthenticationProvider 认证】执行authenticate()方法，查询用户：" + userInfoVO);

                // 校验密码正确性
                if (!passwordEncoder().matches(userAuthCredential, userInfoVO.getUserAuthCredential())) {
                    log.info("【LocalDaoAuthenticationProvider 认证】执行authenticate()方法，账号：{} 密码错误", userAuthIdentifier);
                    throw new BadCredentialsException("用户名或密码不正确");
                }

                return new LocalAuthenticationToken(userInfoVO, userAuthCredential, userInfoVO.getAuthorities());
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return LocalAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

    @Bean
    public AuthenticationProvider emailAuthenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                // 获取 邮箱、验证码
                String userAuthIdentifier = (String) authentication.getPrincipal();
                String userAuthCredential = (String) authentication.getCredentials();
                log.info("【EmailAuthenticationProvider 认证】执行authenticate()方法，获取邮箱：" + userAuthIdentifier);
                log.info("【EmailAuthenticationProvider 认证】执行authenticate()方法，获取验证码：" + userAuthCredential);

                // 校验 邮箱验证码


                // 获取邮箱用户信息
                UserInfoVo userInfoVO = (UserInfoVo) emailUserDetailsService().loadUserByUsername(userAuthIdentifier);

                return new EmailAuthenticationToken(userInfoVO, authentication.getAuthorities());
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return EmailAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

    @Bean
    public AuthenticationProvider phoneAuthenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                // 获取 邮箱、验证码
                String userAuthIdentifier = (String) authentication.getPrincipal();
                String userAuthCredential = (String) authentication.getCredentials();
                log.info("【PhoneAuthenticationProvider 认证】执行authenticate()方法，获取手机号：" + userAuthIdentifier);
                log.info("【PhoneAuthenticationProvider 认证】执行authenticate()方法，获取验证码：" + userAuthCredential);

                // 校验 手机验证码

                // 获取手机号用户信息
                UserInfoVo userInfoVO = (UserInfoVo) phoneUserDetailsService().loadUserByUsername(userAuthIdentifier);
                return new PhoneAuthenticationToken(userInfoVO, authentication.getCredentials());
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return PhoneAuthenticationToken.class.isAssignableFrom(authentication);
            }
        };
    }

    @Bean
    public UserDetailsService localUserDetailsService() {
        return username -> {
            log.info("【LocalUserDetailsServiceImpl 认证】执行loadUserByUsername() 方法，获取账号：" + username);
            UserInfoVo userInfoVO = userService.findByUserAuthIdentifier(username, UserEnum.AuthType.LOCAL.getType());
            if (userInfoVO == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
            return userInfoVO;
        };
    }

    @Bean
    public UserDetailsService emailUserDetailsService() {
        return username -> {
            log.info("【EmailUserDetailsServiceImpl 认证】执行loadUserByUsername() 方法，获取账号：" + username);
            UserInfoVo userInfoVO = userService.findByUserAuthIdentifier(username, UserEnum.AuthType.EMAIL.getType());
            if (userInfoVO == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
            return userInfoVO;
        };
    }

    @Bean
    public UserDetailsService phoneUserDetailsService() {
        return username -> {
            log.info("【PhoneUserDetailsServiceImpl 认证】执行loadUserByUsername() 方法，获取账号：" + username);
            UserInfoVo userInfoVO = userService.findByUserAuthIdentifier(username, UserEnum.AuthType.PHONE.getType());
            if (userInfoVO == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
            return userInfoVO;
        };
    }


}
