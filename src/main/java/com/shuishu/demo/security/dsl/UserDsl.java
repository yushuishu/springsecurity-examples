package com.shuishu.demo.security.dsl;


import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.shuishu.demo.security.common.config.domain.PageDTO;
import com.shuishu.demo.security.common.config.domain.PageVO;
import com.shuishu.demo.security.common.config.jdbc.BaseDsl;
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
 * @description ：
 */
@Component
public class UserDsl extends BaseDsl {
    private final QUser qUser = QUser.user;
    private final QUserAuth qUserAuth = QUserAuth.userAuth;

}
