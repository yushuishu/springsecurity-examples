package com.shuishu.demo.security.common.config.security.provider;


import com.shuishu.demo.security.common.config.security.service.EmailUserDetailsServiceImpl;
import com.shuishu.demo.security.common.config.security.token.EmailAuthenticationToken;
import com.shuishu.demo.security.entity.vo.UserInfoVO;
import jakarta.annotation.Resource;
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
@Component
public class EmailAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private EmailUserDetailsServiceImpl emailUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserInfoVO userInfoVO = (UserInfoVO) emailUserDetailsService.loadUserByUsername(authentication.getPrincipal().toString());
        return new EmailAuthenticationToken(userInfoVO, authentication.getCredentials());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return EmailAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
