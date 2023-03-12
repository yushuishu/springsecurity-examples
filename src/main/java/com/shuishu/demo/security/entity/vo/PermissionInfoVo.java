package com.shuishu.demo.security.entity.vo;


import com.shuishu.demo.security.common.config.domain.BaseVO;
import com.shuishu.demo.security.entity.po.Permission;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;

/**
 * @author ：谁书-ss
 * @date ：2023-01-02 14:09
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@Setter
@Getter
@ToString
public class PermissionInfoVo extends BaseVO<Permission> {
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
     * 权限是否需要授权：true：授权 false：开放
     */
    private Boolean isNeedAuthorization;
    /**
     * 父级权限id（权限分类）
     */
    private Long permissionParentId;
    /**
     * 角色权限关联id
     */
    private Long rolePermissionId;
    /**
     * 角色id
     */
    private Long roleId;
}
