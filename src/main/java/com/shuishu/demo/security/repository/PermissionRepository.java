package com.shuishu.demo.security.repository;


import com.shuishu.demo.security.common.config.jdbc.BaseRepository;
import com.shuishu.demo.security.entity.po.Permission;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:20
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
public interface PermissionRepository extends BaseRepository<Permission, Long> {
    /**
     * 通过权限id 查询所有权限
     *
     * @param permissionIdList 权限id 集合
     * @return 权限集合
     */
    List<Permission> findAllByPermissionIdIn(List<Long> permissionIdList);

    /**
     * 判断权限是否存在
     *
     * @param permissionCode 权限code
     * @param permissionUrl  权限url
     * @return 权限
     */
    Permission findFirstByPermissionCodeOrPermissionUrl(String permissionCode, String permissionUrl);


    /**
     * 根据权限id查询
     *
     * @param permissionId 权限id
     * @return 权限
     */
    Permission findFirstByPermissionId(Long permissionId);

}
