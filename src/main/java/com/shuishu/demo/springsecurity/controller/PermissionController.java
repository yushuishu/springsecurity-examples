package com.shuishu.demo.springsecurity.controller;


import com.shuishu.demo.springsecurity.common.config.domain.ApiResponse;
import com.shuishu.demo.springsecurity.common.config.domain.PageDTO;
import com.shuishu.demo.springsecurity.common.config.domain.PageVO;
import com.shuishu.demo.springsecurity.entity.dto.PermissionAddDTO;
import com.shuishu.demo.springsecurity.entity.vo.PermissionVO;
import com.shuishu.demo.springsecurity.service.PermissionService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:42
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@RestController
@RequestMapping("permission")
public class PermissionController {
    @Resource
    private PermissionService permissionService;

    @PostMapping("add")
    public ApiResponse<String> addPermission(@RequestBody PermissionAddDTO permissionAddDTO){
        permissionService.addPermission(permissionAddDTO);
        return ApiResponse.success();
    }


    @GetMapping("page")
    public ApiResponse<PageVO<PermissionVO>> findPermissionPage(PageDTO pageDTO){
        return ApiResponse.of(permissionService.findPermissionPage(pageDTO));
    }

}
