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
 * @date ：2023-01-01 0:08
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
@Table(name = "ss_role_permission")
@Comment("角色权限关联表")
public class RolePermission extends BasePO {
    @Serial
    private static final long serialVersionUID = 4243243150263214388L;

    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.security.common.config.id.CustomIdGenerator")
    @Comment("角色权限关联id")
    private Long rolePermissionId;

    @Comment("角色id")
    private Long roleId;

    @Comment("权限id")
    private Long permissionId;
}
