package com.shuishu.demo.security.dsl;


import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.shuishu.demo.security.common.config.jdbc.BaseDsl;
import com.shuishu.demo.security.entity.dto.PermissionCacheDto;
import com.shuishu.demo.security.entity.dto.RoleCacheDto;
import com.shuishu.demo.security.entity.po.QPermission;
import com.shuishu.demo.security.entity.po.QRole;
import com.shuishu.demo.security.entity.po.QRolePermission;
import com.shuishu.demo.security.entity.vo.PermissionInfoVo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:19
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@Component
public class PermissionDsl extends BaseDsl {
    final QPermission qPermission = QPermission.permission;
    final QRolePermission qRolePermission = QRolePermission.rolePermission;
    final QRole qRole = QRole.role;

    public List<PermissionInfoVo> findPermissionInfoByRoleIdList(List<Long> roleIdList) {
        return jpaQueryFactory.select(Projections.fields(PermissionInfoVo.class,
                qPermission.permissionId,
                qPermission.permissionCode,
                qPermission.permissionDescription,
                qPermission.permissionUrl,
                qPermission.permissionParentId,
                qRolePermission.roleId,
                qRolePermission.rolePermissionId
        ))
                .from(qRolePermission)
                .leftJoin(qPermission).on(qRolePermission.permissionId.eq(qPermission.permissionId))
                .where(qRolePermission.roleId.in(roleIdList))
                .fetch();
    }


    public List<PermissionCacheDto> findCachePermissionList() {
        return jpaQueryFactory.select(Projections.fields(PermissionCacheDto.class,
                qRole.roleCode,
                qPermission.permissionCode,
                qPermission.permissionUrl,
                qPermission.isNeedAuthorization
        ))
                .from(qPermission)
                .leftJoin(qRolePermission).on(qPermission.permissionId.eq(qRolePermission.permissionId))
                .leftJoin(qRole).on(qRole.roleId.eq(qRolePermission.roleId))
                .transform(GroupBy.groupBy(qPermission).list((
                        Projections.fields(PermissionCacheDto.class,
                                qPermission.permissionUrl,
                                qPermission.permissionCode,
                                qPermission.isNeedAuthorization,
                                GroupBy.list(Projections.fields(RoleCacheDto.class,
                                        qRole.roleCode
                                )).as("roleCacheDtoList"))
                        )
                ));
    }
}
