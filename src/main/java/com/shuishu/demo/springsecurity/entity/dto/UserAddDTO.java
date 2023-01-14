package com.shuishu.demo.springsecurity.entity.dto;


import com.shuishu.demo.springsecurity.common.config.domain.BaseDTO;
import com.shuishu.demo.springsecurity.entity.po.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 14:39
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
@Setter
@Getter
@ToString
public class UserAddDTO extends BaseDTO<User> {
    /**
     * 昵称
     */
    private String nickname;
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
     * 角色集合
     */
    private List<Long> roleIdList;

}
