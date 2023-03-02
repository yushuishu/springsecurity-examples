package com.shuishu.demo.security.common.config.security.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuishu.demo.security.common.config.security.service.MyRememberMeServices;
import com.shuishu.demo.security.common.config.security.handler.MyAuthenticationHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author ：谁书-ss
 * @date ：2022-12-29 22:11
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：登陆过滤器
 * 1、构造方法的参数都可以从容器获取，所以这里也直接注册到容器自动构造
 * 2、继承了 UsernamePasswordAuthenticationFilter，后续我们要用它替换默认的 UsernamePasswordAuthenticationFilter
 * 3、构造函数中，指定了：
 *     登陆成功和失败时的处理方法
 *     记住我功能的组件
 *     登陆使用的路径
 * 4、attemptAuthentication方法中规定了登陆流程：
 *     如果Content-Type是Json，则从Body中获取请求参数，否则从Form表单中获取
 *     从Session的Attribute中获取之前保存的验证码，和用户提供的验证码进行比对
 *     把用户提供的rememberMe字段放到request的Attribute中，供后续MyRememberMeServices获取
 */
@Component
public class UsernamePasswordLoginFilter extends AbstractAuthenticationProcessingFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected UsernamePasswordLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }
}

