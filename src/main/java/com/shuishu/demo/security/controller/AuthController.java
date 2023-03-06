package com.shuishu.demo.security.controller;


import com.shuishu.demo.security.common.config.domain.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("local")
    public ApiResponse<String> local(){

        return ApiResponse.success();
    }

    @PostMapping("phone")
    public ApiResponse<String> phone(){

        return ApiResponse.success();
    }

    @PostMapping("email")
    public ApiResponse<String> email(){

        return ApiResponse.success();
    }

}
