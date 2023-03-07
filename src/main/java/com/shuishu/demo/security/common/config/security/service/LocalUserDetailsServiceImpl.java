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
 * @date ：2022-12-29 22:34
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：本地账号 认证/权限信息
 */
@Service
public class LocalUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserService userService;

    /**
     * 根据用户名查询用户的认证授权信息
     *
     * @param username 用户名 the username identifying the user whose data is required.
     * @return - org.springframework.security.core.userdetails.UserDetails
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoVO userInfoVO = userService.findByUserAuthIdentifier(username, UserEnum.AuthType.LOCAL.getType());
        if (userInfoVO == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return userInfoVO;
    }

}
