package com.shuishu.demo.springsecurity.entity.po;


import com.shuishu.demo.springsecurity.common.config.domain.BasePO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 14:49
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：用户授权信息表
 */
@Setter
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ss_user_auth")
@Comment("用户授权表")
public class UserAuth extends BasePO {
    @Serial
    private static final long serialVersionUID = 5511638296466304494L;

    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.springsecurity.common.config.id.CustomIdGenerator")
    @Comment("用户授权id")
    private Long userAuthId;

    @Comment("用户id")
    private Long userId;

    /**
     * 登录类型
     * LOCAL:本系统账号
     * PHONE:手机号  EMAIL:邮箱
     * QQ:企鹅号  WX:微信号
     * GITHUB: github号  GOOGLE:Google邮箱号
     */
    @Comment("登录类型")
    private String userAuthType;

    /**
     * 登录号，唯一识别码
     */
    @Comment("登录号：唯一识别码")
    private String userAuthIdentifier;

    /**
     * 凭证
     */
    @Comment("凭证")
    private String userAuthCredential;

    /**
     * 昵称
     */
    @Comment("授权昵称")
    private String userAuthNickname;

    /**
     * 头像
     */
    @Comment("授权头像")
    private String userAuthPhoto;

    /**
     * 最后一次登录时间
     */
    @Comment("最后一次登录时间")
    private Date userAuthLastLoginDate;

}
