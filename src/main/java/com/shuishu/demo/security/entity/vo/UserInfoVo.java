package com.shuishu.demo.security.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.*;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:03
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
@Setter
@Getter
@ToString
public class UserInfoVo implements UserDetails {
    @Serial
    private static final long serialVersionUID = -7850778107226817897L;

    /**
     * 用户id
     */
    private Long userId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 个人简介
     */
    private String userAbout;
    /**
     * 头像
     */
    private String userPhoto;
    /**
     * 地址
     */
    private String userAddress;
    /**
     * 职业
     */
    private String userJob;
    /**
     * 用户过期true：没有过期  false：过期
     */
    private Boolean userIsAccountNonExpired;
    /**
     * 用户锁定true：没有锁定  false：被锁定
     */
    private Boolean userIsAccountNonLocked;
    /**
     * 最后一次登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userAuthLastLoginDate;
    /**
     * 用户授权id
     */
    private Long userAuthId;
    /**
     * 授权类型
     */
    private String userAuthType;
    /**
     * 登录号：唯一识别码
     */
    private String userAuthIdentifier;
    /**
     * 凭证信息
     */
    @JsonIgnore
    private String userAuthCredential;
    /**
     * 刷新token
     */
    private String userAuthRefreshToken;
    /**
     * 授权昵称
     */
    private String userAuthNickname;
    /**
     * 授权头像
     */
    private String userAuthPhoto;
    /**
     * 同时登录客户端的人数:最小1最大50（默认1）
     */
    private Integer userMaxLoginClientNumber;
    /**
     * 角色集合
     */
    private List<RoleInfoVo> roleInfoList;
    /**
     * 权限集合
     */
    private List<PermissionInfoVo> permissionInfoList;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        this.permissionInfoList.forEach(t -> authorities.add(new SimpleGrantedAuthority(t.getPermissionUrl())));
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.userAuthCredential;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.userAuthIdentifier;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return this.userIsAccountNonExpired;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.userIsAccountNonLocked;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }


}
