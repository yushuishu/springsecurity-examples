package com.shuishu.demo.security.repository;


import com.shuishu.demo.security.common.config.jdbc.BaseRepository;
import com.shuishu.demo.security.entity.po.UserAuth;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:41
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
public interface UserAuthRepository extends BaseRepository<UserAuth, Long> {
    /**
     * 查询账号信息
     *
     * @param userAuthIdentifier 账号
     * @param userAuthType 类型
     * @return -
     */
    UserAuth findByUserAuthIdentifierAndAndUserAuthType(String userAuthIdentifier, String userAuthType);
}
