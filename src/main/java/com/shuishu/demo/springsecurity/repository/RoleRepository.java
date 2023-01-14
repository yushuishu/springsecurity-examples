package com.shuishu.demo.springsecurity.repository;


import com.shuishu.demo.springsecurity.common.config.jdbc.BaseRepository;
import com.shuishu.demo.springsecurity.entity.po.Role;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:21
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public interface RoleRepository extends BaseRepository<Role, Long> {
    /**
     * 根据角色id集合 查询所有角色
     *
     * @param roleIdList 角色id 集合
     * @return 角色集合
     */
    List<Role> findAllByRoleIdIn(List<Long> roleIdList);
}
