package com.shuishu.demo.security.service.impl;


import com.shuishu.demo.security.dsl.RoleDsl;
import com.shuishu.demo.security.dsl.UserAuthDsl;
import com.shuishu.demo.security.entity.vo.PermissionInfoVO;
import com.shuishu.demo.security.entity.vo.RoleInfoVO;
import com.shuishu.demo.security.entity.vo.UserInfoVO;
import com.shuishu.demo.security.enums.UserEnum;
import com.shuishu.demo.security.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2022-12-29 22:40
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService {
    @Resource
    private UserAuthDsl userAuthDsl;
    @Resource
    private RoleDsl roleDsl;

    @Override
    public UserInfoVO findByUserAuthIdentifier(String userAuthIdentifier, String authType) {
        UserInfoVO userInfoVO = userAuthDsl.findByUserAuthIdentifier(userAuthIdentifier, authType);
        if (userInfoVO == null){
            return null;
        }
        // 角色
        List<RoleInfoVO> roleInfoList = roleDsl.findRoleInfoByUserId(userInfoVO.getUserId());
        if (!ObjectUtils.isEmpty(roleInfoList)){
            // 权限
            List<PermissionInfoVO> permissionInfoList = new ArrayList<>();
            roleInfoList.forEach(t -> {
                if (!ObjectUtils.isEmpty(t.getPermissionInfoList())){
                    permissionInfoList.addAll(t.getPermissionInfoList());
                }
            });
            userInfoVO.setPermissionInfoList(permissionInfoList);
        }

        return userInfoVO;
    }

}
