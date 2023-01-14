package com.shuishu.demo.springsecurity.entity.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuishu.demo.springsecurity.common.config.domain.BaseVO;
import com.shuishu.demo.springsecurity.entity.po.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.io.Serial;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:26
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVO extends BaseVO<User> {
    @Serial
    private static final long serialVersionUID = 9085380458828742302L;

    private Long userId;

    private String username;

    private String password;

    private Boolean userExpired;

    private Boolean userLocked;

}
