package com.shuishu.demo.security.dsl;


import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.common.config.jdbc.BaseDsl;
import com.shuishu.demo.security.entity.po.Permission;
import com.shuishu.demo.security.entity.po.QPermission;
import com.shuishu.demo.security.entity.po.QRole;
import com.shuishu.demo.security.entity.po.QRolePermission;
import com.shuishu.demo.security.entity.vo.PermissionInfoVO;
import com.shuishu.demo.security.entity.vo.RoleInfoVO;
import com.shuishu.demo.security.entity.vo.RoleVO;
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
    private final QRolePermission qRolePermission = QRolePermission.rolePermission;
    private final QPermission qPermission = QPermission.permission;

    public PageVO<RoleVO> findRolePage(PageDTO pageDTO) {
        PageVO<RoleVO> page = pageDTO.toPageVO(RoleVO.class);

        List<Long> count = jpaQueryFactory.select(qRole.roleId).from(qRole).fetch();
        page.setTotalElements(count.size());

        List<RoleVO> fetch = jpaQueryFactory.select(Projections.fields(RoleVO.class,
                        qRole.roleId,
                        qRole.roleName,
                        qRole.roleCode
                ))
                .from(qRole)
                .orderBy(qRole.updateTime.desc())
                .offset(page.getOffset()).limit(page.getPageSize())
                .fetch();
        page.setDataList(fetch);

        return page;
    }

    public List<RoleInfoVO> findRoleInfoByUserId(Long userId) {
        return jpaQueryFactory.from(qRole)
                .innerJoin(qRolePermission).on(qRole.roleId.eq(qRolePermission.roleId))
                .leftJoin(qPermission).on(qRolePermission.permissionId.eq(qPermission.permissionId))
                .transform(GroupBy.groupBy(qRole).list((
                        Projections.fields(RoleInfoVO.class,
                                qRole.roleId,
                                qRole.roleName,
                                qRole.roleCode,
                                GroupBy.list(Projections.fields(PermissionInfoVO.class,
                                        qPermission.permissionId,
                                        qPermission.permissionCode,
                                        qPermission.permissionDescription,
                                        qPermission.permissionUrl,
                                        qPermission.permissionParentId
                                )).as("permissionInfoList")
                        )
                )));
    }
}
