package com.shuishu.demo.security.service;


import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.entity.dto.PermissionAddDTO;
import com.shuishu.demo.security.entity.vo.PermissionVO;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:42
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public interface PermissionService {
    /**
     * 添加权限
     *
     * @param permissionAddDTO 权限数据
     */
    void addPermission(PermissionAddDTO permissionAddDTO);

    /**
     * 分页查询
     *
     * @param pageDTO 分页
     * @return 权限分页数据
     */
    PageVO<PermissionVO> findPermissionPage(PageDTO pageDTO);
}
