package com.shuishu.demo.security.common.config.security.provider;


import com.shuishu.demo.security.common.config.security.service.PhoneUserDetailsServiceImpl;
import com.shuishu.demo.security.common.config.security.token.PhoneAuthenticationToken;
import com.shuishu.demo.security.entity.vo.UserInfoVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author ：谁书-ss
 * @date ：2023-03-05 14:33
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：手机号登录Provider
 * <p></p>
 */
@Slf4j
@Component
public class PhoneAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private PhoneUserDetailsServiceImpl phoneUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取 邮箱、验证码
        String userAuthIdentifier = (String) authentication.getPrincipal();
        String userAuthCredential = (String) authentication.getCredentials();
        log.info("【PhoneAuthenticationProvider 认证】执行authenticate()方法，获取手机号：" + userAuthIdentifier);
        log.info("【PhoneAuthenticationProvider 认证】执行authenticate()方法，获取验证码：" + userAuthCredential);

        // 校验 手机验证码

        // 获取手机号用户信息
        UserInfoVO userInfoVO = (UserInfoVO) phoneUserDetailsService.loadUserByUsername(userAuthIdentifier);
        return new PhoneAuthenticationToken(userInfoVO, authentication.getCredentials());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
