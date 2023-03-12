package com.shuishu.demo.security.controller;


import com.shuishu.demo.security.common.config.domain.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：谁书-ss
 * @date ：2023-03-06 20:43
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：测试接口权限访问
 * <p></p>
 */
@RestController
@RequestMapping("test")
public class TestController {

    @PostMapping("add")
    public ApiResponse<String> add() {
        return ApiResponse.success("添加成功", "");
    }

    @GetMapping("page")
    public ApiResponse<String> page() {
        return ApiResponse.success("分页查询成功", "");
    }

    @GetMapping("details")
    public ApiResponse<String> details() {
        return ApiResponse.success("查询详情成功", "");
    }

    @PostMapping("update")
    public ApiResponse<String> update() {
        return ApiResponse.success("修改成功", "");
    }

    @PostMapping("delete")
    public ApiResponse<String> delete() {
        return ApiResponse.success("删除成功", "");
    }

    @GetMapping("enable")
    public ApiResponse<String> enable() {
        return ApiResponse.success("开启关闭成功", "");
    }

}
