package com.shuishu.demo.security.service.impl;


import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.common.config.exception.BusinessException;
import com.shuishu.demo.security.dsl.RoleDsl;
import com.shuishu.demo.security.dsl.UserAuthDsl;
import com.shuishu.demo.security.dsl.UserDsl;
import com.shuishu.demo.security.entity.dto.UserAddDTO;
import com.shuishu.demo.security.entity.dto.UserDTO;
import com.shuishu.demo.security.entity.dto.UserUpdateDTO;
import com.shuishu.demo.security.entity.po.Role;
import com.shuishu.demo.security.entity.po.User;
import com.shuishu.demo.security.entity.po.UserAuth;
import com.shuishu.demo.security.entity.po.UserRole;
import com.shuishu.demo.security.entity.vo.UserVO;
import com.shuishu.demo.security.enums.UserEnum;
import com.shuishu.demo.security.repository.RoleRepository;
import com.shuishu.demo.security.repository.UserAuthRepository;
import com.shuishu.demo.security.repository.UserRepository;
import com.shuishu.demo.security.repository.UserRoleRepository;
import com.shuishu.demo.security.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2022-12-29 22:40
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserDsl userDsl;
    @Resource
    private UserAuthRepository userAuthRepository;
    @Resource
    private UserAuthDsl userAuthDsl;
    @Resource
    private RoleRepository roleRepository;
    @Resource
    private RoleDsl roleDsl;
    @Resource
    private UserRoleRepository userRoleRepository;


    @Override
    public void addUser(UserAddDTO userAddDTO) {
        if (!UserEnum.AuthType.verifyType(userAddDTO.getUserAuthType())){
            throw new BusinessException("账号类型不存在");
        }
        if (ObjectUtils.isEmpty(userAddDTO.getRoleIdList())){
            throw new BusinessException("用户角色不能为空");
        }
        List<Role> roleList = roleRepository.findAllByRoleIdIn(userAddDTO.getRoleIdList());
        if (ObjectUtils.isEmpty(roleList) || roleList.size() != userAddDTO.getRoleIdList().size()){
            throw new BusinessException("角色不存在");
        }

        // 保存用户基本信息
        User user = new User();
        BeanUtils.copyProperties(userAddDTO, user);
        user.setUserExpired(false);
        user.setUserLocked(false);
        User dbUser = userRepository.saveAndFlush(user);

        // 保存用户账号信息
        UserAuth userAuth = new UserAuth();
        BeanUtils.copyProperties(userAddDTO, userAuth);
        userAuth.setUserId(dbUser.getUserId());
        userAuthRepository.save(userAuth);

        // 保存用户角色关联关系
        Date currentDate = new Date();
        List<UserRole> list = new ArrayList<>();
        userAddDTO.getRoleIdList().forEach(t -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(t);
            userRole.setUserId(dbUser.getUserId());
            userRole.setCreateTime(currentDate);
            userRole.setUpdateTime(currentDate);
            list.add(userRole);
        });
        userRoleRepository.saveAll(list);
    }

    @Override
    public PageVO<UserVO> findUserPage(UserDTO userDTO, PageDTO pageDTO) {
        return userDsl.findUserPage(userDTO, pageDTO);
    }

    @Override
    public void updateByUserId(UserUpdateDTO userUpdateDTO) {

    }

}
