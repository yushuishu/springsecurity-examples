package com.shuishu.demo.springsecurity.service;


import com.shuishu.demo.springsecurity.common.config.domain.PageDTO;
import com.shuishu.demo.springsecurity.common.config.domain.PageVO;
import com.shuishu.demo.springsecurity.entity.dto.RoleAddDTO;
import com.shuishu.demo.springsecurity.entity.vo.RoleVO;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:43
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public interface RoleService {
    /**
     * 添加角色
     *
     * @param roleAddDTO -
     */
    void addRole(RoleAddDTO roleAddDTO);

    /**
     * 角色分页查询
     *
     * @param pageDTO 分页
     * @return 角色分页数据
     */
    PageVO<RoleVO> findRolePage(PageDTO pageDTO);
}
