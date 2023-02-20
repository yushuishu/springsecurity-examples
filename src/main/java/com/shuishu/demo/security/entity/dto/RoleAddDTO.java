package com.shuishu.demo.security.entity.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:47
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
public class RoleAddDTO {

    private String roleName;

    private String roleCode;

    private List<Long> permissionIdList;
}
