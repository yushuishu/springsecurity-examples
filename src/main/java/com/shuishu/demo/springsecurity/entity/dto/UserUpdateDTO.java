package com.shuishu.demo.springsecurity.entity.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:38
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
public class UserUpdateDTO {
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
    /**
     * 用户授权id
     */
    private Long userAuthId;
    /**
     * 登录类型
     * LOCAL:本系统账号
     * PHONE:手机号  EMAIL:邮箱
     * QQ:企鹅号  WX:微信号
     * GITHUB: github号  GOOGLE:Google邮箱号
     */
    private String userAuthType;
    /**
     * 登录号，唯一识别码
     */
    private String userAuthIdentifier;
    /**
     * 凭证
     */
    private String userAuthCredential;
    /**
     * 昵称
     */
    private String userAuthNickname;
    /**
     * 头像
     */
    private String userAuthPhoto;

}
