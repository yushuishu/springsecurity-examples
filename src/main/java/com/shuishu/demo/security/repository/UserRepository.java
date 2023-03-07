package com.shuishu.demo.security.repository;


import com.shuishu.demo.security.common.config.jdbc.BaseRepository;
import com.shuishu.demo.security.entity.po.User;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:23
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
public interface UserRepository extends BaseRepository<User, Long> {
    /**
     * 用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    User findByUserId(Long userId);
}
