package com.shuishu.demo.security.dsl;


import com.querydsl.core.types.Projections;
import com.shuishu.demo.security.common.config.jdbc.BaseDsl;
import com.shuishu.demo.security.entity.po.QUser;
import com.shuishu.demo.security.entity.po.QUserAuth;
import com.shuishu.demo.security.entity.vo.UserInfoVo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:41
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@Component
public class UserAuthDsl extends BaseDsl {
    private final QUserAuth qUserAuth = QUserAuth.userAuth;
    private final QUser qUser = QUser.user;

    public UserInfoVo findByUserAuthIdentifier(String userAuthIdentifier, String userAuthType) {
        if (!StringUtils.hasText(userAuthIdentifier) || !StringUtils.hasText(userAuthType)) {
            return null;
        }
        return jpaQueryFactory.select(Projections.fields(UserInfoVo.class,
                        qUser.userId, qUser.nickname,
                        qUser.nickname,
                        qUser.userAbout,
                        qUser.userPhoto,
                        qUser.userAddress,
                        qUser.userJob,
                        qUser.userIsAccountNonExpired,
                        qUser.userIsAccountNonLocked,
                        qUser.userLastLoginDate,
                        qUser.userMaxLoginClientNumber,
                        qUserAuth.userAuthId,
                        qUserAuth.userAuthType,
                        qUserAuth.userAuthIdentifier,
                        qUserAuth.userAuthCredential,
                        qUserAuth.userAuthRefreshToken,
                        qUserAuth.userAuthNickname,
                        qUserAuth.userAuthPhoto
                ))
                .from(qUserAuth)
                .leftJoin(qUser).on(qUserAuth.userId.eq(qUser.userId))
                .where(qUserAuth.userAuthIdentifier.eq(userAuthIdentifier).and(qUserAuth.userAuthType.eq(userAuthType)))
                .fetchOne();
    }
}
