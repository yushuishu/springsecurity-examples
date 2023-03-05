package com.shuishu.demo.security.common.config.security.utils;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 13:40
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：Spring Security 工具类
 */
public class SpringSecurityUtil {
    public static final String LOGIN_URL_LOCAL = "/api/shuishu/demo/auth/local";
    public static final String LOGIN_URL_PHONE = "/api/shuishu/demo/auth/phone";
    public static final String LOGIN_URL_EMAIL = "/api/shuishu/demo/auth/email";

    public static final String LOGIN_USERNAME_KEY = "userAuthIdentifier";
    public static final String LOGIN_PASSWORD_KEY = "userAuthCredential";
    public static final String LOGIN_CAPTCHA= "captcha";


    /**
     * 登录
     *
     * @param username    用户名
     * @param password    密码
     */
    public static void login(String username, String password) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        SecurityContextHolder.getContext().setAuthentication(token);
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
