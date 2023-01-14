/*
package com.shuishu.demo.springsecurity.common.config.security;


import com.shuishu.demo.springsecurity.entity.po.User;
import com.shuishu.demo.springsecurity.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

*/
/**
 * @author ：谁书-ss
 * @date ：2022-12-29 22:34
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：认证/权限信息
 *//*

@Service
public class MyUserDetailsServiceImpl implements UserDetailsService, UserDetailsPasswordService {
    @Resource
    private UserService userService;

    */
/**
     * 修改密码
     *
     * @param user        用户 the user to modify the password for
     * @param newPassword 新密码 the password to change to, encoded by the configured
     *                    {@code PasswordEncoder}
     * @return org.springframework.security.core.userdetails.UserDetails
     *//*

    @Override
    public UserDetails updatePassword(UserDetails user, String newPassword) {
        User dbUser = userService.findByUserName(user.getUsername());
        dbUser.setPassword(newPassword);
        userService.updateByUserId(dbUser);
        return dbUser.createUser()
                .authorities(new ArrayList<>())
                .build();
    }

    */
/**
     * 根据用户名查询用户的认证授权信息
     *
     * @param username 用户名 the username identifying the user whose data is required.
     * @return - org.springframework.security.core.userdetails.UserDetails
     * @throws UsernameNotFoundException 异常
     *//*

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user.createUser()
                .authorities(new ArrayList<>())
                .build();
    }

    */
/**
     * 当前用户
     *
     * @return 当前用户
     *//*

    public User currentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return userService.findByUserName(username);
    }

}
*/
