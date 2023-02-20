package com.shuishu.demo.security.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuishu.demo.security.common.config.domain.BaseVO;
import com.shuishu.demo.security.entity.po.UserAuth;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-01-02 12:02
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthVO extends BaseVO<UserAuth> {
    @Serial
    private static final long serialVersionUID = -7659171458829688722L;

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
    /**
     * 最后一次登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userAuthLastLoginDate;
}
