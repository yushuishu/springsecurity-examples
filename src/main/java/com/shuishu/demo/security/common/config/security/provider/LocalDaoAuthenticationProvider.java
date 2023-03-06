package com.shuishu.demo.security.common.config.security.provider;


import com.shuishu.demo.security.common.config.security.service.LocalUserDetailsServiceImpl;
import com.shuishu.demo.security.common.config.security.token.LocalAuthenticationToken;
import com.shuishu.demo.security.entity.vo.UserInfoVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author ：谁书-ss
 * @date ：2023-03-05 15:00
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 * <p></p>
 */
@Slf4j
@Component
public class LocalDaoAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private LocalUserDetailsServiceImpl localUserDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取 账号、密码
        String userAuthIdentifier = (String) authentication.getPrincipal();
        String userAuthCredential = (String) authentication.getCredentials();
        log.info("【LocalDaoAuthenticationProvider 认证】执行authenticate()方法，获取账号：" + userAuthIdentifier);
        log.info("【LocalDaoAuthenticationProvider 认证】执行authenticate()方法，获取密码：" + userAuthCredential);

        // 获取账号用户信息
        UserInfoVO userInfoVO = (UserInfoVO) localUserDetailsService.loadUserByUsername(userAuthIdentifier);
        log.info("【LocalDaoAuthenticationProvider 认证】执行authenticate()方法，查询用户：" + userInfoVO);

        // 校验密码正确性
        if (!passwordEncoder.matches(userAuthCredential, userInfoVO.getUserAuthCredential())){
            log.info("【LocalDaoAuthenticationProvider 认证】执行authenticate()方法，账号：{} 密码错误", userAuthIdentifier);
            throw new BadCredentialsException("用户名或密码不正确");
        }

        return new LocalAuthenticationToken(userInfoVO, userAuthCredential, userInfoVO.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return LocalAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
