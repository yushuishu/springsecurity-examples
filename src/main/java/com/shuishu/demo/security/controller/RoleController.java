package com.shuishu.demo.security.controller;


import com.shuishu.demo.security.common.config.domain.ApiResponse;
import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.entity.dto.RoleAddDTO;
import com.shuishu.demo.security.entity.vo.RoleVO;
import com.shuishu.demo.security.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:41
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@RestController
@RequestMapping("role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @PostMapping("add")
    public ApiResponse<String> addRole(RoleAddDTO roleAddDTO){
        roleService.addRole(roleAddDTO);
        return ApiResponse.success();
    }

    @GetMapping("page1")
    public ApiResponse<PageVO<RoleVO>> findRolePage1(PageDTO pageDTO){
        return ApiResponse.of(roleService.findRolePage(pageDTO));
    }

    @GetMapping("page2")
    public ApiResponse<PageVO<RoleVO>> findRolePage2(PageDTO pageDTO){
        return ApiResponse.of(roleService.findRolePage(pageDTO));
    }


    @GetMapping("page3")
    public ApiResponse<PageVO<RoleVO>> findRolePage3(PageDTO pageDTO){
        return ApiResponse.of(roleService.findRolePage(pageDTO));
    }


    @GetMapping("page4")
    public ApiResponse<PageVO<RoleVO>> findRolePage4(PageDTO pageDTO){
        return ApiResponse.of(roleService.findRolePage(pageDTO));
    }

    @GetMapping("page5")
    public ApiResponse<PageVO<RoleVO>> findRolePage5(PageDTO pageDTO){
        return ApiResponse.of(roleService.findRolePage(pageDTO));
    }

    @GetMapping("page6")
    public ApiResponse<PageVO<RoleVO>> findRolePage6(PageDTO pageDTO){
        return ApiResponse.of(roleService.findRolePage(pageDTO));
    }


}
