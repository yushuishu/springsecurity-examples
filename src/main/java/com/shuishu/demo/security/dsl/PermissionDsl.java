package com.shuishu.demo.security.dsl;


import com.querydsl.core.types.Projections;
import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.common.config.jdbc.BaseDsl;
import com.shuishu.demo.security.entity.po.QPermission;
import com.shuishu.demo.security.entity.po.QRolePermission;
import com.shuishu.demo.security.entity.vo.PermissionInfoVO;
import com.shuishu.demo.security.entity.vo.PermissionVO;
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
    private final QPermission qPermission = QPermission.permission;
    private final QRolePermission qRolePermission = QRolePermission.rolePermission;

    public List<PermissionInfoVO> findPermissionInfoByRoleIdList(List<Long> roleIdList) {
        return jpaQueryFactory.select(Projections.fields(PermissionInfoVO.class,
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
}
