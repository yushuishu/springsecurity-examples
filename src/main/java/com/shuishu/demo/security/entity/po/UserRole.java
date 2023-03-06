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

/**
 * @author ：谁书-ss
 * @date ：2022-12-31 23:58
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@Setter
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "ss_user_role")
@Comment("用户角色表")
public class UserRole extends BasePO {
    @Serial
    private static final long serialVersionUID = -1002951400656466356L;

    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.security.common.config.id.CustomIdGenerator")
    @Comment("用户角色id")
    private Long userRoleId;

    @Comment("用户id")
    private Long userId;

    @Comment("角色id")
    private Long roleId;
}
