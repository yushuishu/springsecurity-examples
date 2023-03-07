package com.shuishu.demo.security.service.impl;


import com.shuishu.demo.security.common.config.exception.BusinessException;
import com.shuishu.demo.security.common.config.security.token.EmailAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.LocalAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.PhoneAuthenticationToken;
import com.shuishu.demo.security.entity.vo.UserInfoVO;
import com.shuishu.demo.security.enums.UserEnum;
import com.shuishu.demo.security.service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author ：谁书-ss
 * @date ：2023/3/7 14:41
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public UserInfoVO login(String name, String pwd, UserEnum.AuthType authType) {
        Authentication authentication = null;
        if (UserEnum.AuthType.LOCAL.equals(authType)){
            authentication = authenticationManager.authenticate(new LocalAuthenticationToken(name, pwd));
        }else if (UserEnum.AuthType.EMAIL.equals(authType)){
            authentication = authenticationManager.authenticate(new EmailAuthenticationToken(name, pwd));
        }else if (UserEnum.AuthType.PHONE.equals(authType)){
            authentication = authenticationManager.authenticate(new PhoneAuthenticationToken(name, pwd));
        }else {
            throw new BusinessException("不支持的登录方式");
        }
        UserInfoVO userInfoVO = (UserInfoVO) authentication.getPrincipal();

        // 生成token，或者在provider中生成

        return userInfoVO;
    }
}
