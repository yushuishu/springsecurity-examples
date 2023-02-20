package com.shuishu.demo.security.entity.dto;


import com.shuishu.demo.security.common.config.domain.BaseDTO;
import com.shuishu.demo.security.entity.po.Permission;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:50
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
public class PermissionAddDTO extends BaseDTO<Permission> {
    private String permissionCode;

    private String permissionUrl;

    private String permissionDescription;

    private Long permissionParentId;

}
