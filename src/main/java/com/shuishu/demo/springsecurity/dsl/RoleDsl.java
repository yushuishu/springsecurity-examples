package com.shuishu.demo.springsecurity.dsl;


import com.querydsl.core.types.Projections;
import com.shuishu.demo.springsecurity.common.config.domain.PageDTO;
import com.shuishu.demo.springsecurity.common.config.domain.PageVO;
import com.shuishu.demo.springsecurity.common.config.jdbc.BaseDsl;
import com.shuishu.demo.springsecurity.entity.po.QRole;
import com.shuishu.demo.springsecurity.entity.vo.RoleVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:21
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Component
public class RoleDsl extends BaseDsl {
    private final QRole qRole = QRole.role;


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
}
