package com.shuishu.demo.security.entity.vo;


import com.shuishu.demo.security.common.config.domain.BaseVO;
import com.shuishu.demo.security.entity.po.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.List;

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
public class RoleInfoVO extends BaseVO<Role> {
    @Serial
    private static final long serialVersionUID = 3491624868548321484L;

    /**
     * 角色id
     */
    private Long roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 角色code
     */
    private String roleCode;
    /**
     * 权限集合
     */
    private List<PermissionInfoVO> permissionInfoList;
}
