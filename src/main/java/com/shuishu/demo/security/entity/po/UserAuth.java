package com.shuishu.demo.security.entity.po;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.shuishu.demo.security.common.config.domain.BasePO;
import com.shuishu.demo.security.enums.UserEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 14:49
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：用户授权信息表
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
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.security.common.config.id.CustomIdGenerator")
    @Comment("用户授权id")
    private Long userAuthId;
    @Comment("用户id")
    private Long userId;
    /**
     * 授权类型
     * {@link UserEnum.AuthType}
     */
    @Comment("授权类型")
    private String userAuthType;
    @Comment("登录号：唯一识别码")
    @Column(nullable = false, unique = true)
    private String userAuthIdentifier;
    @Comment("凭证信息")
    private String userAuthCredential;
    @Comment("刷新token（不是本地记住我，是OAuth第三方的刷新token）")
    private String userAuthRefreshToken;
    @Comment("授权昵称")
    private String userAuthNickname;
    @Comment("授权头像")
    private String userAuthPhoto;

}
