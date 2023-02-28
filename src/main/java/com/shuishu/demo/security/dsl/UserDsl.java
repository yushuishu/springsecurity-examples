package com.shuishu.demo.security.dsl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.common.config.jdbc.BaseDsl;
import com.shuishu.demo.security.entity.dto.UserDTO;
import com.shuishu.demo.security.entity.po.QUser;
import com.shuishu.demo.security.entity.po.QUserAuth;
import com.shuishu.demo.security.entity.vo.UserVO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:22
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Component
public class UserDsl extends BaseDsl {
    private final QUser qUser = QUser.user;
    private final QUserAuth qUserAuth = QUserAuth.userAuth;


    public PageVO<UserVO> findUserPage(UserDTO userDTO, PageDTO pageDTO) {
        PageVO<UserVO> page = pageDTO.toPageVO(UserVO.class);

        BooleanBuilder builder = new BooleanBuilder();
        if (userDTO.getUserId() != null){
            builder.and(qUser.userId.eq(userDTO.getUserId()));
        }
        if (userDTO.getUserExpired() != null){
            builder.and(qUser.isAccountNonExpired.eq(userDTO.getUserExpired()));
        }
        if (userDTO.getUserLocked() != null){
            builder.and(qUser.isAccountNonLocked.eq(userDTO.getUserLocked()));
        }
        if (StringUtils.hasText(userDTO.getNickname())){
            builder.and(qUser.nickname.like("%" + userDTO.getNickname() + "%"));
        }

        List<Long> count = jpaQueryFactory.select(qUser.userId).from(qUser).where(builder).fetch();
        page.setTotalElements(count.size());

        List<UserVO> fetch = jpaQueryFactory.select(Projections.fields(UserVO.class,
                        qUser.userId,
                        qUser.nickname,
                        qUser.isAccountNonExpired,
                        qUser.isAccountNonLocked,
                        qUser.userPhoto
                ))
                .from(qUser)
                .where(builder)
                .orderBy(qUser.updateTime.desc())
                .offset(page.getOffset()).limit(page.getPageSize())
                .fetch();
        page.setDataList(fetch);

        return page;
    }


}
