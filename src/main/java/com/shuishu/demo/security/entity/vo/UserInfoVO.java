package com.shuishu.demo.security.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @Description ：
 */
@Setter
@Getter
@ToString
public class UserInfoVO implements UserDetails {
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
     * 用户是否过期
     */
    private Boolean userExpired;
    /**
     * 用户是被锁定
     */
    private Boolean userLocked;
    /**
     * 用户授权id
     */
    private Long userAuthId;
    /**
     * 登录类型
     * LOCAL:本系统账号
     * PHONE:手机号  EMAIL:邮箱
     * QQ:企鹅号  WX:微信号
     * GITHUB: github号  GOOGLE:Google邮箱号
     */
    private String userAuthType;
    /**
     * 登录号，唯一识别码
     */
    private String userAuthIdentifier;
    /**
     * 凭证
     */
    private String userAuthCredential;
    /**
     * 昵称
     */
    private String userAuthNickname;
    /**
     * 头像
     */
    private String userAuthPhoto;
    /**
     * 最后一次登录时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date userAuthLastLoginDate;
    /**
     * 角色集合
     */
    private List<RoleInfoVO> roleInfoList;
    /**
     * 权限集合
     */
    private List<PermissionInfoVO> permissionInfoList;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        this.permissionInfoList.forEach(t -> authorities.add(new SimpleGrantedAuthority(t.getPermissionCode())));
        return authorities;
    }

    @Override
    public String getPassword() {
        return userAuthCredential;
    }

    @Override
    public String getUsername() {
        return userAuthIdentifier;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !userExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !userLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
