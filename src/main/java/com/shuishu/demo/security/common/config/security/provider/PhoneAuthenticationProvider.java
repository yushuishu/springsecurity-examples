package com.shuishu.demo.security.common.config.security.provider;


import com.shuishu.demo.security.common.config.security.service.PhoneUserDetailsServiceImpl;
import com.shuishu.demo.security.common.config.security.token.PhoneAuthenticationToken;
import com.shuishu.demo.security.entity.vo.UserInfoVO;
import jakarta.annotation.Resource;
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
@Component
public class PhoneAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private PhoneUserDetailsServiceImpl phoneUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserInfoVO userInfoVO = (UserInfoVO) phoneUserDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        return new PhoneAuthenticationToken(userInfoVO, authentication.getCredentials());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
