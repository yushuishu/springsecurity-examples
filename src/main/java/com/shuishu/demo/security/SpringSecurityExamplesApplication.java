package com.shuishu.demo.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;


/**
 * @author ：谁书-ss
 * @date   ： 2022-12-28 21:21
 * @IDE    ：IntelliJ IDEA
 * @Motto  ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 * {@link <a href="https://docs.spring.io/spring-security/reference/whats-new.html">...</a>}
 *
 * SpringSecurity 实现功能：
 *      1、使用用户名+密码+验证码+记住我功能进行登陆
 *      2、CSRF校验  将Token交给Redis管理
 *      3、将记住我功能持久化到数据库
 *      4、第三方登录（测试）
 *
 * <p></p>
 * 基础组件
 *      验证码
 *      MyUserDetailsServiceImpl (认证/权限信息)
 *      MyAuthenticationHandler (Handler)
 *      MyRememberMeServices (记住我)
 * 核心组件
 *      UsernamePasswordLoginFilter (登陆过滤器)
 *      SecurityConfig (核心配置)
 */
@SpringBootApplication
public class SpringSecurityExamplesApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityExamplesApplication.class, args);
    }

    @Bean
    public MethodValidationPostProcessor mvp(){
        return new MethodValidationPostProcessor();
    }

}
