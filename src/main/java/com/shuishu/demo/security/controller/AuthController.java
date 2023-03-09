package com.shuishu.demo.security.controller;


import com.shuishu.demo.security.common.config.domain.ApiResponse;
import com.shuishu.demo.security.common.config.security.SpringSecurityUtil;
import com.shuishu.demo.security.entity.dto.UserLoginDto;
import com.shuishu.demo.security.entity.vo.UserInfoVo;
import com.shuishu.demo.security.enums.UserEnum;
import com.shuishu.demo.security.service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:44
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：认证/授权
 */
@RestController
@RequestMapping("auth")
public class AuthController {
    @Resource
    private AuthService authService;

    @PostMapping("local")
    public ApiResponse<UserInfoVo> local(@RequestBody UserLoginDto userLoginDTO){
        System.out.println("登录----------- local");
        return ApiResponse.of(authService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.LOCAL));
    }

    @PostMapping("email")
    public ApiResponse<UserInfoVo> email(@RequestBody UserLoginDto userLoginDTO){
        System.out.println("登录----------- email");
        return ApiResponse.of(authService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.EMAIL));
    }

    @PostMapping("phone")
    public ApiResponse<UserInfoVo> phone(@RequestBody UserLoginDto userLoginDTO){
        System.out.println("登录----------- phone");
        SpringSecurityUtil.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.LOCAL);
        return ApiResponse.of(authService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword(), UserEnum.AuthType.PHONE));
    }



}
