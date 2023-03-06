package com.shuishu.demo.security.common.config.security.provider;


import com.shuishu.demo.security.common.config.security.service.EmailUserDetailsServiceImpl;
import com.shuishu.demo.security.common.config.security.token.EmailAuthenticationToken;
import com.shuishu.demo.security.entity.vo.UserInfoVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author ：谁书-ss
 * @date ：2023-03-05 14:32
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：邮箱登录Provider
 * <p></p>
 */
@Slf4j
@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private EmailUserDetailsServiceImpl emailUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取 邮箱、验证码
        String userAuthIdentifier = (String) authentication.getPrincipal();
        String userAuthCredential = (String) authentication.getCredentials();
        log.info("【EmailAuthenticationProvider 认证】执行authenticate()方法，获取邮箱：" + userAuthIdentifier);
        log.info("【EmailAuthenticationProvider 认证】执行authenticate()方法，获取验证码：" + userAuthCredential);

        // 校验 邮箱验证码


        // 获取邮箱用户信息
        UserInfoVO userInfoVO = (UserInfoVO) emailUserDetailsService.loadUserByUsername(userAuthIdentifier);

        return new EmailAuthenticationToken(userInfoVO, authentication.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
