package com.shuishu.demo.security.entity.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-03-10 23:22
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：缓存权限集合
 * <p></p>
 */
@Setter
@Getter
@ToString
public class PermissionCacheDto {
    private String permissionCode;

    private String permissionUrl;

    private Boolean isNeedAuthorization;

    private List<RoleCacheDto> roleCacheDtoList;

}
