package com.shuishu.demo.security.common.utils;


import cn.hutool.core.lang.hash.Hash64;
import cn.hutool.crypto.digest.MD5;
import com.shuishu.demo.security.entity.vo.UserInfoVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author ：谁书-ss
 * @date ：2023-03-09 21:36
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：Token 工具类
 * <p></p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenUtils {
    /**
     * token
     */
    @Value("${shuishu.token.auth-token}")
    private String ymlAuthToken;
    /**
     * 刷新 token
     */
    @Value("${shuishu.token.refresh-token}")
    private String ymlRefreshToken;
    /**
     * 秘钥
     */
    @Value("${shuishu.token.secret}")
    private String ymlSecret;
    /**
     * token 的有效期
     */
    @Value("${shuishu.token.expire-time}")
    private int ymlExpireTime;
    /**
     * 刷新token 的有效期
     */
    @Value("${shuishu.token.refresh-token-expire-time}")
    private int ymlRefreshTokenExpireTime;
    /**
     * auth-token 距离过期时间 expire-time 还有多少分钟，重新设置有效时间为 expire-time
     */
    @Value("${shuishu.token.surplus-refresh-time}")
    private int ymlSurplusRefreshTime;


    private final RedisUtils redisUtils;


    public String createToken() {
        return PasswordUtils.encrypt(ymlSecret, UUID.randomUUID().toString().replace("-", ""));
    }

    public boolean setUserInfoVo(UserInfoVo userInfoVo, HttpServletResponse response) {
        if (userInfoVo != null){
            String authToken = createToken();
            String refreshToken = createToken();
            if (StringUtils.hasText(authToken) && StringUtils.hasText(refreshToken)){
                redisUtils.strSet(authToken, userInfoVo, ymlExpireTime);
                redisUtils.strSet(refreshToken, userInfoVo, ymlRefreshTokenExpireTime);
                response.setHeader(ymlAuthToken, authToken);
                response.setHeader(ymlRefreshToken, refreshToken);
                return true;
            }
        }
        return false;
    }

    public UserInfoVo getUserInfoVo(HttpServletRequest request, HttpServletResponse response) {
        UserInfoVo userInfoVo = null;
        String headerAuthToken = request.getHeader(ymlAuthToken);
        if (StringUtils.hasText(headerAuthToken)) {
            Object userInfoObjForAuthToken = redisUtils.strGet(headerAuthToken);
            if (userInfoObjForAuthToken == null) {
                // 尝试获取刷新 token
                String headerRefreshToken = request.getHeader(ymlRefreshToken);
                if (StringUtils.hasText(headerRefreshToken)) {
                    Object userInfoObjForRefreshToken = redisUtils.strGet(headerRefreshToken);
                    if (userInfoObjForRefreshToken == null) {
                        return null;
                    }
                    userInfoVo = (UserInfoVo) userInfoObjForRefreshToken;
                    redisUtils.strSet(headerAuthToken, userInfoVo, ymlExpireTime);
                    return userInfoVo;
                }
                return null;
            } else {
                userInfoVo = (UserInfoVo) userInfoObjForAuthToken;
                long expire = redisUtils.getExpire(headerAuthToken);
                if (expire < ymlSurplusRefreshTime) {
                    // 刷新token
                    redisUtils.strSet(headerAuthToken, userInfoVo, ymlExpireTime);
                }
                return userInfoVo;
            }
        } else {
            // 请求头没有 AuthToken 只有 RefreshToken 也尽最大可能的尝试获取用户信息，创建新的 token，进行缓存
            String headerRefreshToken = request.getHeader(ymlRefreshToken);
            if (StringUtils.hasText(headerRefreshToken)) {
                Object userInfoObjForRefreshToken = redisUtils.strGet(headerRefreshToken);
                if (userInfoObjForRefreshToken == null) {
                    return null;
                }
                userInfoVo = (UserInfoVo) userInfoObjForRefreshToken;
                String newAuthToken = createToken();
                redisUtils.strSet(newAuthToken, userInfoVo, ymlExpireTime);
                response.setHeader(ymlAuthToken, newAuthToken);
                return userInfoVo;
            }
            return null;
        }
    }


}
