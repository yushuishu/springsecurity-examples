package com.shuishu.demo.springsecurity.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuishu.demo.springsecurity.common.config.domain.BaseVO;
import com.shuishu.demo.springsecurity.entity.po.Permission;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 16:02
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionVO extends BaseVO<Permission> {
    @Serial
    private static final long serialVersionUID = -86694503222454689L;

    private Long permissionId;

    private String permissionCode;

    private String permissionUrl;

    private String permissionDescription;

    private Long permissionParentId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
