package com.shuishu.demo.security.entity.dto;


import com.shuishu.demo.security.common.config.domain.BaseDTO;
import com.shuishu.demo.security.entity.po.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 14:34
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
public class UserDTO extends BaseDTO<User> {
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像
     */
    private String userPhoto;
    /**
     * 用户是否过期
     */
    private Boolean userExpired;
    /**
     * 用户是被锁定
     */
    private Boolean userLocked;
}
