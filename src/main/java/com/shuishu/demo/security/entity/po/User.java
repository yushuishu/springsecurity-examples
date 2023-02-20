package com.shuishu.demo.security.entity.po;


import com.shuishu.demo.security.common.config.domain.BasePO;
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
 * @date ：2022-12-29 22:38
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：用户基础信息表
 */
@Setter
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ss_user")
@Comment(value = "用户表")
public class User extends BasePO {
    @Serial
    private static final long serialVersionUID = 7399511491764319903L;

    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.springsecurity.common.config.id.CustomIdGenerator")
    @Comment(value = "用户id")
    private Long userId;
    /**
     * 昵称
     */
    @Comment("昵称")
    private String nickname;
    /**
     * 个人简介
     */
    @Comment("个人简介")
    private String userAbout;
    /**
     * 头像
     */
    @Comment("头像")
    private String userPhoto;

    @Comment("用户是否过期")
    private Boolean userExpired;

    @Comment("用户是被锁定")
    private Boolean userLocked;

}
