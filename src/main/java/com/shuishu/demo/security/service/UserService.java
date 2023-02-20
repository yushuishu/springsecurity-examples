package com.shuishu.demo.security.service;


import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.entity.dto.UserAddDTO;
import com.shuishu.demo.security.entity.dto.UserDTO;
import com.shuishu.demo.security.entity.dto.UserUpdateDTO;
import com.shuishu.demo.security.entity.vo.UserVO;

/**
 * @author ：谁书-ss
 * @date ：2022-12-29 22:39
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public interface UserService {
    /**
     * 添加用户
     *
     * @param userAddDTO 添加的用户数据
     */
    void addUser(UserAddDTO userAddDTO);

    /**
     * 分页查询
     *
     * @param userDTO 用户dto
     * @param pageDTO 用户vo
     * @return 用户分页数据
     */
    PageVO<UserVO> findUserPage(UserDTO userDTO, PageDTO pageDTO);

    /**
     * 更据用户id 更新用户信息
     *
     * @param userUpdateDTO 用户
     */
    void updateByUserId(UserUpdateDTO userUpdateDTO);

}
