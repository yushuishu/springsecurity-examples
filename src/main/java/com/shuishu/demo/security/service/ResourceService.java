package com.shuishu.demo.security.service;



import com.shuishu.demo.security.entity.dto.PermissionCacheDto;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-03-10 22:31
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：资源管理：角色、权限
 * <p></p>
 */
public interface ResourceService {
    /**
     * 查询所有权限集合
     *
     * @return 权限集合
     */
    List<PermissionCacheDto> findCachePermissionList();

}
