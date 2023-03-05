package com.shuishu.demo.security.common.config.security.service;


import com.shuishu.demo.security.entity.vo.UserInfoVO;
import com.shuishu.demo.security.enums.UserEnum;
import com.shuishu.demo.security.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ：谁书-ss
 * @date ：2023-03-05 14:55
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：手机号  认证/权限信息
 * <p></p>
 */
@Service
public class PhoneUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoVO userInfoVO = userService.findByUserAuthIdentifier(username, UserEnum.AuthType.PHONE);
        if (userInfoVO == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return userInfoVO;
    }
}
