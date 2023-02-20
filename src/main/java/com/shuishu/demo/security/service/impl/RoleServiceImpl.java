package com.shuishu.demo.security.service.impl;


import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.common.config.exception.BusinessException;
import com.shuishu.demo.security.dsl.PermissionDsl;
import com.shuishu.demo.security.dsl.RoleDsl;
import com.shuishu.demo.security.dsl.RolePermissionDsl;
import com.shuishu.demo.security.entity.dto.RoleAddDTO;
import com.shuishu.demo.security.entity.po.Permission;
import com.shuishu.demo.security.entity.po.Role;
import com.shuishu.demo.security.entity.po.RolePermission;
import com.shuishu.demo.security.entity.vo.RoleVO;
import com.shuishu.demo.security.repository.PermissionRepository;
import com.shuishu.demo.security.repository.RolePermissionRepository;
import com.shuishu.demo.security.repository.RoleRepository;
import com.shuishu.demo.security.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:43
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private RoleDsl roleDsl;
    @Resource
    private PermissionRepository permissionRepository;
    @Resource
    private PermissionDsl permissionDsl;
    @Resource
    private RolePermissionRepository rolePermissionRepository;
    @Resource
    private RolePermissionDsl rolePermissionDsl;


    @Override
    public void addRole(RoleAddDTO roleAddDTO) {
        if (ObjectUtils.isEmpty(roleAddDTO.getPermissionIdList())){
            throw new BusinessException("权限不能为空");
        }
        List<Permission> permissionList = permissionRepository.findAllByPermissionIdIn(roleAddDTO.getPermissionIdList());
        if (ObjectUtils.isEmpty(permissionList) || permissionList.size() != roleAddDTO.getPermissionIdList().size()){
            throw new BusinessException("权限不存在");
        }

        // 保存角色
        Role role = new Role();
        role.setRoleName(roleAddDTO.getRoleName());
        role.setRoleCode(roleAddDTO.getRoleCode());
        Role dbRole = roleRepository.saveAndFlush(role);

        // 保存角色权限关联关系
        List<RolePermission> list = new ArrayList<>();
        Date currentDate = new Date();
        roleAddDTO.getPermissionIdList().forEach(t -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(t);
            rolePermission.setRoleId(dbRole.getRoleId());
            rolePermission.setCreateTime(currentDate);
            rolePermission.setUpdateTime(currentDate);
            list.add(rolePermission);
        });
        rolePermissionRepository.saveAll(list);
    }

    @Override
    public PageVO<RoleVO> findRolePage(PageDTO pageDTO) {
        return roleDsl.findRolePage(pageDTO);
    }
}
