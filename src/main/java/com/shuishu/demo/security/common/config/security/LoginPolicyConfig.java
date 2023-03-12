package com.shuishu.demo.security.common.config.security;


import com.shuishu.demo.security.enums.UserEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author ：谁书-ss
 * @date ：2023-03-12 12:49
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：登录策略
 * <p></p>
 */
@Component
public class LoginPolicyConfig {

    @Value("${shuishu.login-policy}")
    private String loginPolicy;


    public String getLoginPolicy() {
        if (UserEnum.LoginPolicy.existLoginPolicy(loginPolicy)) {
            return loginPolicy;
        }
        // 返回默认的登录策略
        return UserEnum.LoginPolicy.ONE.name();
    }

}
