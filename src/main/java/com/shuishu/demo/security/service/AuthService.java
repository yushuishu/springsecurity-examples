package com.shuishu.demo.security.service;


import com.shuishu.demo.security.entity.vo.UserInfoVo;
import com.shuishu.demo.security.enums.UserEnum;
import jakarta.servlet.http.HttpServletResponse;

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
     * @param isRememberMe -
     * @param response -
     * @return -
     */
    UserInfoVo login(String name, String pwd, UserEnum.AuthType authType, Boolean isRememberMe, HttpServletResponse response);
}
