package com.shuishu.demo.security.service.impl;


import com.shuishu.demo.security.common.utils.RedisUtils;
import com.shuishu.demo.security.dsl.PermissionDsl;
import com.shuishu.demo.security.dsl.RoleDsl;
import com.shuishu.demo.security.entity.dto.PermissionCacheDto;
import com.shuishu.demo.security.enums.RedisKeyEnum;
import com.shuishu.demo.security.repository.PermissionRepository;
import com.shuishu.demo.security.repository.RoleRepository;
import com.shuishu.demo.security.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-03-10 22:30
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：资源管理：角色、权限
 * <p></p>
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final RoleRepository roleRepository;
    private final RoleDsl roleDsl;
    private final PermissionRepository permissionRepository;
    private final PermissionDsl permissionDsl;
    private final RedisUtils redisUtils;

    @Override
    public List<PermissionCacheDto> findCachePermissionList() {
        List<Object> objectList = redisUtils.listGet(RedisKeyEnum.KEY_PERMISSION_URL_LIST.getKey(), 0, -1);
        if (ObjectUtils.isEmpty(objectList)){
            List<PermissionCacheDto> permissionCacheDtoList = permissionDsl.findCachePermissionList();
            redisUtils.listSet(RedisKeyEnum.KEY_PERMISSION_URL_LIST.getKey(), permissionCacheDtoList);
            return permissionCacheDtoList;
        }
        List<PermissionCacheDto> result = new ArrayList<>();
        for (Object o : objectList) {
            if (o instanceof PermissionCacheDto permissionCacheDto){
                result.add(permissionCacheDto);
            }
        }
        return result;
    }

}
