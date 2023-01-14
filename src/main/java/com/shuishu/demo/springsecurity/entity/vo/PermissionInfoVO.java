package com.shuishu.demo.springsecurity.entity.vo;


import com.shuishu.demo.springsecurity.common.config.domain.BaseVO;
import com.shuishu.demo.springsecurity.entity.po.Permission;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;

import java.io.Serial;

/**
 * @author ：谁书-ss
 * @date ：2023-01-02 14:09
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
public class PermissionInfoVO extends BaseVO<Permission> {
    @Serial
    private static final long serialVersionUID = 6581320655357167673L;
    /**
     * 权限id
     */
    private Long permissionId;
    /**
     * 权限code
     */
    private String permissionCode;
    /**
     * 权限url
     */
    private String permissionUrl;
    /**
     * 权限描述
     */
    private String permissionDescription;
    /**
     * 父级权限id（权限分类）
     */
    private Long permissionParentId;
}
