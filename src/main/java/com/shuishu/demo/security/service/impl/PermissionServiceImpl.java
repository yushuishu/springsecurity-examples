package com.shuishu.demo.security.service.impl;


import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.common.config.exception.BusinessException;
import com.shuishu.demo.security.dsl.PermissionDsl;
import com.shuishu.demo.security.entity.dto.PermissionAddDTO;
import com.shuishu.demo.security.entity.po.Permission;
import com.shuishu.demo.security.entity.vo.PermissionVO;
import com.shuishu.demo.security.repository.PermissionRepository;
import com.shuishu.demo.security.service.PermissionService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

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
public class PermissionServiceImpl implements PermissionService {
    @Resource
    private PermissionRepository permissionRepository;
    @Resource
    private PermissionDsl permissionDsl;


    @Override
    public void addPermission(PermissionAddDTO permissionAddDTO) {
        Permission permission = permissionAddDTO.toPo(Permission.class);
        if (StringUtils.hasText(permission.getPermissionCode()) || StringUtils.hasText(permission.getPermissionUrl())){
            Permission tempPermission = permissionRepository.findFirstByPermissionCodeOrPermissionUrl(permission.getPermissionCode(), permission.getPermissionUrl());
            Objects.requireNonNull(tempPermission, "”权限code“或“权限url”已存在");
            Objects.requireNonNull(permissionAddDTO.getPermissionParentId(), "权限分类不能为空");
        }else {
            if (StringUtils.hasText(permission.getPermissionUrl()) || StringUtils.hasText(permission.getPermissionCode())){
                throw new BusinessException("权限分类错误");
            }
        }
        if (permissionAddDTO.getPermissionParentId() != null){
            Permission parentPermission = permissionRepository.findFirstByPermissionId(permissionAddDTO.getPermissionParentId());
            Objects.requireNonNull(parentPermission, "权限分类不存在");
        }
        permissionRepository.save(permission);
    }

    @Override
    public PageVO<PermissionVO> findPermissionPage(PageDTO pageDTO) {
        return permissionDsl.findPermissionPage(pageDTO);
    }
}
