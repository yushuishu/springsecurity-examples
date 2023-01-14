package com.shuishu.demo.springsecurity.controller;


import com.shuishu.demo.springsecurity.common.config.domain.ApiResponse;
import com.shuishu.demo.springsecurity.common.config.domain.PageDTO;
import com.shuishu.demo.springsecurity.common.config.domain.PageVO;
import com.shuishu.demo.springsecurity.entity.dto.UserAddDTO;
import com.shuishu.demo.springsecurity.entity.dto.UserDTO;
import com.shuishu.demo.springsecurity.entity.vo.UserInfoVO;
import com.shuishu.demo.springsecurity.entity.vo.UserVO;
import com.shuishu.demo.springsecurity.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:25
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;


    @PostMapping("add")
    public ApiResponse<String> addUser(UserAddDTO userAddDTO){
        userService.addUser(userAddDTO);
        return ApiResponse.success();
    }

    @GetMapping("page")
    public ApiResponse<PageVO<UserVO>> findUserPage(UserDTO userDTO, PageDTO pageDTO){
        return ApiResponse.of(userService.findUserPage(userDTO, pageDTO));
    }

}
