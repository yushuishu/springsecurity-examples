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
 * @date ：2022-12-31 23:49
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
@Table(name = "ss_role")
@Comment("角色表")
public class Role extends BasePO {
    @Serial
    private static final long serialVersionUID = -6675981271899865871L;

    @Id
    @GeneratedValue(generator = "CustomIdGenerator")
    @GenericGenerator(name = "CustomIdGenerator", strategy = "com.shuishu.demo.security.common.config.id.CustomIdGenerator")
    @Comment("角色id")
    private Long roleId;

    @Comment("角色名称")
    @Column(nullable = false)
    private String roleName;

    @Comment("角色code")
    @Column(nullable = false, unique = true)
    private String roleCode;

}
