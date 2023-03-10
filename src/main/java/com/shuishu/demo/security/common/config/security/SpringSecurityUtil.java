package com.shuishu.demo.security.common.config.security;


import com.shuishu.demo.security.common.config.exception.BusinessException;
import com.shuishu.demo.security.common.config.security.token.EmailAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.LocalAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.PhoneAuthenticationToken;
import com.shuishu.demo.security.enums.UserEnum;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 13:40
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：Spring Security 工具类
 */
public class SpringSecurityUtil {
    public static final String LOGOUT_URL = "/api/shuishu/demo/auth/logout";
    public static final String LOGIN_URL_LOCAL = "/api/shuishu/demo/auth/local";
    public static final String LOGIN_URL_PHONE = "/api/shuishu/demo/auth/phone";
    public static final String LOGIN_URL_EMAIL = "/api/shuishu/demo/auth/email";

    public static final String LOGIN_USERNAME_KEY = "userAuthIdentifier";
    public static final String LOGIN_PASSWORD_KEY = "userAuthCredential";
    public static final String LOGIN_CAPTCHA= "captcha";


    public static String[] ignoreUrlArray(){
        return new String[]{
                "/api/shuishu/demo/doc.html",
                "/api/shuishu/demo/webjars/**",
                "/api/shuishu/demo/v3/api-docs/**",
                //yml配置文件有访问前缀，所以SpringSecurity这里是不能加前缀的
                "/auth/**",
                //过滤器判断使用
                "/api/shuishu/demo/auth/**"
        };
    }

    public static boolean existsInIgnoreUrlArray(String requestUri){
        for (String ignoreUrl : ignoreUrlArray()) {
            if (requestUri.contains(ignoreUrl.replace("/**", ""))){
                return true;
            }
        }
        return false;
    }

    /**
     * 登录
     *
     * @param username    用户名
     * @param password    密码
     */
    public static void login(String username, String password, UserEnum.AuthType authType) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Authentication authentication = null;
        if (UserEnum.AuthType.LOCAL.equals(authType)){

            SecurityContextHolder.getContext().setAuthentication(new LocalAuthenticationToken(username, password));
        }else if (UserEnum.AuthType.EMAIL.equals(authType)){
            SecurityContextHolder.getContext().setAuthentication(new EmailAuthenticationToken(username, password));
        }else if (UserEnum.AuthType.PHONE.equals(authType)){
            SecurityContextHolder.getContext().setAuthentication(new PhoneAuthenticationToken(username, password));
        }else {
            throw new BusinessException("不支持的登录方式");
        }
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

    /**
     * 获取当前登录用户名
     *
     * @return 用户名
     */
    public static String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
    }

    /**
     * 判断是否已登录
     *
     * @return true：已登录  false：未登录
     */
    public static boolean isLogin() {
        // SecurityContextHolder.getContext().getAuthentication().isAuthenticated() 永远都是true
        // 所以即使是当前用户是 anonymousUser 时，也不能用来判断登录状态，必须要判断是否是 AnonymousAuthenticationToken 类型。
        return !(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken);
    }

}
