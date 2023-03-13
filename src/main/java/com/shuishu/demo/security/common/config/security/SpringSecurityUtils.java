package com.shuishu.demo.security.common.config.security;


import com.shuishu.demo.security.common.config.exception.BusinessException;
import com.shuishu.demo.security.common.config.security.token.EmailAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.LocalAuthenticationToken;
import com.shuishu.demo.security.common.config.security.token.PhoneAuthenticationToken;
import com.shuishu.demo.security.enums.UserEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 13:40
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：Spring Security 工具类
 */
public class SpringSecurityUtils {
    public static final String LOGOUT_URL = "/api/shuishu/demo/auth/logout";
    public static final String LOGIN_URL_LOCAL = "/api/shuishu/demo/auth/local";
    public static final String LOGIN_URL_EMAIL = "/api/shuishu/demo/auth/email";
    public static final String LOGIN_URL_PHONE = "/api/shuishu/demo/auth/phone";

    public static final String LOGIN_USERNAME_KEY = "userAuthIdentifier";
    public static final String LOGIN_USERNAME_FRONT_KEY = "username";
    public static final String LOGIN_PASSWORD_KEY = "userAuthCredential";
    public static final String LOGIN_PASSWORD_FRONT_KEY = "password";
    public static final String LOGIN_CAPTCHA = "captcha";


    public static String getAuthType(String uri) {
        if (StringUtils.hasText(uri)) {
            return switch (uri) {
                case LOGIN_URL_LOCAL -> UserEnum.AuthType.LOCAL.getType();
                case LOGIN_URL_EMAIL -> UserEnum.AuthType.EMAIL.getType();
                case LOGIN_URL_PHONE -> UserEnum.AuthType.PHONE.getType();
                default -> null;
            };
        }
        return null;
    }

    public static String[] ignoreUrlArray() {
        //yml配置文件有访问前缀context-path  SpringSecurity 这里就不能加前缀
        return new String[]{
                "/doc.html",
                "/webjars/**",
                "/v3/api-docs/**",
                "/auth/**",
                //加前缀，是为了过滤器判断使用
                "/api/shuishu/demo/auth/**"
        };
    }

    public static boolean existsInIgnoreUrlArray(String requestUri) {
        for (String ignoreUrl : ignoreUrlArray()) {
            if (requestUri.contains(ignoreUrl.replace("/**", ""))) {
                return true;
            }
        }
        return false;
    }

    public static boolean existsInIgnoreUrlArrayForDb(String requestUri, List<String> isNotAuthorizationUrlList) {
        if (!ObjectUtils.isEmpty(isNotAuthorizationUrlList)) {
            for (String dbIgnoreUrl : isNotAuthorizationUrlList) {
                if (requestUri.contains(dbIgnoreUrl.replace("/**", ""))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    public static void login(String username, String password, UserEnum.AuthType authType) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        Authentication authentication = null;
        if (UserEnum.AuthType.LOCAL.equals(authType)) {
            SecurityContextHolder.getContext().setAuthentication(new LocalAuthenticationToken(username, password));
        } else if (UserEnum.AuthType.EMAIL.equals(authType)) {
            SecurityContextHolder.getContext().setAuthentication(new EmailAuthenticationToken(username, password));
        } else if (UserEnum.AuthType.PHONE.equals(authType)) {
            SecurityContextHolder.getContext().setAuthentication(new PhoneAuthenticationToken(username, password));
        } else {
            throw new BusinessException("不支持的登录方式");
        }
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
    }

}
