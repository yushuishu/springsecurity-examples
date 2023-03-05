package com.shuishu.demo.security.common.config.security.provider;


import com.shuishu.demo.security.common.config.security.config.PasswordEncoderConfig;
import com.shuishu.demo.security.common.config.security.service.LocalUserDetailsServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

/**
 * @author ：谁书-ss
 * @date ：2023-03-05 15:00
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 * <p></p>
 */
@Configuration
public class LocalDaoAuthenticationProvider {
    @Resource
    private LocalUserDetailsServiceImpl localUserDetailsService;
    @Resource
    private PasswordEncoderConfig passwordEncoderConfig;

    /**
     * 默认AuthenticationProvider，如果创建了自定义AuthenticationProvider，则默认的就不会被注入到 AuthenticationManager
     * 所以如果还想保留默认的，需要手动创建 Bean，并在 AuthenticationManager中注入
     *
     * @return DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoderConfig.passwordEncoder());
        provider.setUserDetailsService(localUserDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

}
