package com.shuishu.demo.security.common.config.security.filter;


import com.shuishu.demo.security.common.config.security.handler.MyAuthenticationHandler;
import com.shuishu.demo.security.common.config.security.token.LocalAuthenticationToken;
import com.shuishu.demo.security.common.config.security.utils.SpringSecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @author ：谁书-ss
 * @date ：2022-12-29 22:11
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：本地账号登录过滤器
 * 1、继承了 AbstractAuthenticationProcessingFilter，实现类中的抽象方法 attemptAuthentication()
 * 2、重写构造方法，因为security中的 AbstractAuthenticationProcessingFilter类没有无参构造方法
 *    所以子类需要通过 super()方法显式的调用父类构造方法。
 *    构造方法的参数都可以从容器获取，所以这里也直接注册到容器自动构造
 * 3、构造函数中，指定了：
 *     拦截的登录请求和请求方式
 *     自定义认证管理器
 *     认证成功和失败时的处理器
 *     登陆使用的路径
 *     记住我功能的组件
 * 4、attemptAuthentication方法中规定了登陆流程：
 *     判断请求类型是否为 json ：如果Content-Type是Json，则从Body中获取请求参数，否则从Form表单中获取
 *     从请求体中获取登录参数   ：从Session的Attribute中获取之前保存的验证码，和用户提供的验证码进行比对
 *     ”记住我“功能参数       ： 把用户提供的rememberMe字段放到request的Attribute中，供后续 MyRememberMeService获取
 *     调用认证管理器的 authenticate()方法
 */
public class LocalLoginFilter extends AbstractAuthenticationProcessingFilter {

    public LocalLoginFilter(AuthenticationManager authenticationManager,
                               MyAuthenticationHandler myAuthenticationHandler) {
        // 登录路径，方式、认证管理器
        super(new AntPathRequestMatcher(SpringSecurityUtil.LOGIN_URL_LOCAL, RequestMethod.POST.name()), authenticationManager);
        // 认证成功
        setAuthenticationSuccessHandler(myAuthenticationHandler);
        // 认证失败
        setAuthenticationFailureHandler(myAuthenticationHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String userAuthIdentifier = request.getParameter(SpringSecurityUtil.LOGIN_USERNAME_KEY);
        userAuthIdentifier = StringUtils.hasText(userAuthIdentifier) ? userAuthIdentifier.trim() : "";
        String userAuthCredential = request.getParameter(SpringSecurityUtil.LOGIN_PASSWORD_KEY);
        userAuthCredential = StringUtils.hasText(userAuthCredential) ? userAuthCredential.trim() : "";
        LocalAuthenticationToken localAuthenticationToken = new LocalAuthenticationToken(userAuthIdentifier, userAuthCredential);
        localAuthenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
        return getAuthenticationManager().authenticate(localAuthenticationToken);
    }

}

