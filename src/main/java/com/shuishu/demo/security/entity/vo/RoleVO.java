package com.shuishu.demo.security.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.shuishu.demo.security.common.config.domain.BaseVO;
import com.shuishu.demo.security.entity.po.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.util.Date;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:59
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVO extends BaseVO<Role> {
    @Serial
    private static final long serialVersionUID = 1286143384701589148L;

    private Long roleId;

    private String roleName;

    private String roleCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
