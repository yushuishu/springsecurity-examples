package com.shuishu.demo.security.service.impl;


import com.shuishu.demo.security.dsl.PermissionDsl;
import com.shuishu.demo.security.dsl.RoleDsl;
import com.shuishu.demo.security.dsl.UserAuthDsl;
import com.shuishu.demo.security.entity.po.User;
import com.shuishu.demo.security.entity.po.UserAuth;
import com.shuishu.demo.security.entity.vo.PermissionInfoVo;
import com.shuishu.demo.security.entity.vo.RoleInfoVo;
import com.shuishu.demo.security.entity.vo.UserInfoVo;
import com.shuishu.demo.security.repository.UserAuthRepository;
import com.shuishu.demo.security.repository.UserRepository;
import com.shuishu.demo.security.service.UserService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserAuthRepository userAuthRepository;
    private final UserAuthDsl userAuthDsl;
    private final UserRepository userRepository;
    private final RoleDsl roleDsl;
    private final PermissionDsl permissionDsl;

    @Override
    public UserInfoVo findByUserAuthIdentifier(String userAuthIdentifier, String authType) {
        UserAuth userAuth = userAuthRepository.findByUserAuthIdentifierAndAndUserAuthType(userAuthIdentifier, authType);
        if (userAuth == null) {
            return null;
        }
        User user = userRepository.findByUserId(userAuth.getUserId());
        if (user == null) {
            return null;
        }

        UserInfoVo userInfoVO = new UserInfoVo();
        BeanUtils.copyProperties(userAuth, userInfoVO);
        BeanUtils.copyProperties(user, userInfoVO);

        // 角色
        List<RoleInfoVo> roleInfoList = roleDsl.findRoleInfoByUserId(userInfoVO.getUserId());
        if (!ObjectUtils.isEmpty(roleInfoList)) {
            userInfoVO.setRoleInfoList(roleInfoList);
            // 权限
            List<PermissionInfoVo> permissionInfoList = permissionDsl.findPermissionInfoByRoleIdList(roleInfoList.stream().map(RoleInfoVo::getRoleId).collect(Collectors.toList()));
            if (!ObjectUtils.isEmpty(permissionInfoList)) {
                userInfoVO.setPermissionInfoList(permissionInfoList);
            }
        }

        return userInfoVO;
    }

}
