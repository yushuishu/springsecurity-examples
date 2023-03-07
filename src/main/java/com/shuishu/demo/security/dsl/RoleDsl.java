package com.shuishu.demo.security.dsl;


import com.querydsl.core.types.Projections;
import com.shuishu.demo.security.common.config.jdbc.BaseDsl;
import com.shuishu.demo.security.entity.po.*;
import com.shuishu.demo.security.entity.vo.RoleInfoVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:21
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@Component
public class RoleDsl extends BaseDsl {
    private final QRole qRole = QRole.role;
    private final QUserRole qUserRole = QUserRole.userRole;

    public List<RoleInfoVO> findRoleInfoByUserId(Long userId) {
        return jpaQueryFactory.select(Projections.fields(RoleInfoVO.class,
                        qRole.roleId,
                        qRole.roleName,
                        qRole.roleCode
                ))
                .from(qUserRole)
                .innerJoin(qRole).on(qUserRole.roleId.eq(qRole.roleId))
                .where(qUserRole.userId.eq(userId)).fetch();
    }
}
