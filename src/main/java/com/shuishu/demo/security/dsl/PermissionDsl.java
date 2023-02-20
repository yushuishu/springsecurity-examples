package com.shuishu.demo.security.dsl;


import com.querydsl.core.types.Projections;
import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.common.config.jdbc.BaseDsl;
import com.shuishu.demo.security.entity.po.QPermission;
import com.shuishu.demo.security.entity.vo.PermissionVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:19
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Component
public class PermissionDsl extends BaseDsl {
    private final QPermission qPermission = QPermission.permission;


    public PageVO<PermissionVO> findPermissionPage(PageDTO pageDTO) {
        PageVO<PermissionVO> page = pageDTO.toPageVO(PermissionVO.class);

        List<Long> count = jpaQueryFactory.select(qPermission.permissionId).from(qPermission).fetch();
        page.setTotalElements(count.size());

        List<PermissionVO> fetch = jpaQueryFactory.select(Projections.fields(PermissionVO.class,
                        qPermission.permissionId,
                        qPermission.permissionCode,
                        qPermission.permissionUrl,
                        qPermission.permissionDescription,
                        qPermission.permissionParentId
                ))
                .from(qPermission)
                .orderBy(qPermission.updateTime.desc()).offset(page.getOffset()).limit(page.getPageSize())
                .fetch();
        page.setDataList(fetch);

        return page;
    }
}
