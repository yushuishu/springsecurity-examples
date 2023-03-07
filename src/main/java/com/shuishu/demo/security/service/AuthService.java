package com.shuishu.demo.security.service;


import com.shuishu.demo.security.entity.vo.UserInfoVO;
import com.shuishu.demo.security.enums.UserEnum;

/**
 * @author ：谁书-ss
 * @date ：2023/3/7 14:41
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
public interface AuthService {

    /**
     * 登录授权
     *
     * @param name -
     * @param pwd -
     * @param authType -
     * @return -
     */
    UserInfoVO login(String name, String pwd, UserEnum.AuthType authType);
}
