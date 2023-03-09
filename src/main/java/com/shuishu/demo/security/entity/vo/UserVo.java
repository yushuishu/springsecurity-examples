package com.shuishu.demo.security.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuishu.demo.security.common.config.domain.BaseVO;
import com.shuishu.demo.security.entity.po.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 0:26
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVo extends BaseVO<User> {
    @Serial
    private static final long serialVersionUID = 9085380458828742302L;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 个人简介
     */
    private String userAbout;
    /**
     * 头像
     */
    private String userPhoto;
    /**
     * 地址
     */
    private String userAddress;
    /**
     * 职业
     */
    private String userJob;
    /**
     * 用户是否过期
     */
    private Boolean userExpired;
    /**
     * 用户是被锁定
     */
    private Boolean userLocked;
    /**
     * 最后一次登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userAuthLastLoginDate;
    /**
     * 授权类型
     */
    private String userAuthType;
    /**
     * 登录号：唯一识别码
     */
    private String userAuthIdentifier;
    /**
     * 授权昵称
     */
    private String userAuthNickname;
    /**
     * 授权头像
     */
    private String userAuthPhoto;
}
