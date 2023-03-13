package com.shuishu.demo.security.common.utils;


import com.shuishu.demo.security.entity.dto.AuthTokenCacheDto;
import com.shuishu.demo.security.entity.vo.UserInfoVo;
import com.shuishu.demo.security.enums.RedisKeyEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    @Value("${shuishu.token.remember-me-token}")
    private String ymlRememberMeToken;
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
    @Value("${shuishu.token.remember-me-token-expire-time}")
    private int ymlRememberMeTokenExpireTime;
    /**
     * auth-token 距离过期时间 expire-time 还有多少分钟，重新设置有效时间为 expire-time
     */
    @Value("${shuishu.token.surplus-refresh-time}")
    private int ymlSurplusRefreshTime;


    private final RedisUtils redisUtils;


    public String createToken() {
        return PasswordUtils.encrypt(ymlSecret, UUID.randomUUID().toString().replace("-", ""));
    }

    /**
     * 登录成功，创建 token 缓存
     *
     * @param userInfoVo   登录成功的用户信息
     * @param isRememberMe 是否记住我（是否创建 RememberMeToken）
     * @param response     响应
     * @return 是否成功
     */
    public boolean setUserInfoVo(UserInfoVo userInfoVo, Boolean isRememberMe, HttpServletResponse response) {
        if (userInfoVo != null) {
            String authToken = createToken();
            if (StringUtils.hasText(authToken)) {
                redisUtils.strSet(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + authToken, userInfoVo, ymlExpireTime);
                response.setHeader(ymlAuthToken, authToken);
                if (Boolean.TRUE.equals(isRememberMe)) {
                    String rememberMeToken = createToken();
                    redisUtils.strSet(RedisKeyEnum.GROUP_REMEMBER_ME_TOKEN.getKey() + rememberMeToken, userInfoVo, ymlRememberMeTokenExpireTime);
                    response.setHeader(ymlRememberMeToken, rememberMeToken);
                }
                // 缓存当前在线的客户端
                AuthTokenCacheDto authTokenCacheDto = new AuthTokenCacheDto();
                List<AuthTokenCacheDto> userAuthTokenList = getUserAllAuthTokenList(userInfoVo.getUserId());
                if (ObjectUtils.isEmpty(userAuthTokenList)) {
                    userAuthTokenList = new ArrayList<>();
                }
                authTokenCacheDto.setAuthToken(authToken);
                authTokenCacheDto.setCreateTime(new Date());
                userAuthTokenList.add(authTokenCacheDto);
                setUserAuthTokenList(userInfoVo.getUserId(), userAuthTokenList);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取用户信息
     *
     * @param request  优先通过 AuthToken 获取用户信息，获取不到，再使用 RememberMeToken。
     * @param response AuthToken 不存在，RememberMeToken 存在，则设置响应新的 AuthToken
     * @return 用户信息
     */
    public UserInfoVo getUserInfoVo(HttpServletRequest request, HttpServletResponse response) {
        String headerAuthToken = request.getHeader(ymlAuthToken);
        if (StringUtils.hasText(headerAuthToken)) {
            return getUserInfoVo(request, headerAuthToken);
        } else {
            // 请求头没有 AuthToken 只有 RememberMeToken 也尽最大可能的尝试获取用户信息，创建新的 token，进行缓存
            String headerRememberMeToken = request.getHeader(ymlRememberMeToken);
            if (StringUtils.hasText(headerRememberMeToken)) {
                Object userInfoObjForRememberMeToken = redisUtils.strGet(RedisKeyEnum.GROUP_REMEMBER_ME_TOKEN.getKey() + headerRememberMeToken);
                if (userInfoObjForRememberMeToken == null) {
                    return null;
                }
                UserInfoVo userInfoVo = (UserInfoVo) userInfoObjForRememberMeToken;
                String newAuthToken = createToken();
                redisUtils.strSet(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + newAuthToken, userInfoVo, ymlExpireTime);
                response.setHeader(ymlAuthToken, newAuthToken);
                return userInfoVo;
            }
            return null;
        }
    }

    /**
     * 当前内部类，提取的公共逻辑 方法，通过 AuthToken 获取用户信息
     *
     * @param request         -
     * @param headerAuthToken -
     * @return 用户信息
     */
    private UserInfoVo getUserInfoVo(HttpServletRequest request, String headerAuthToken) {
        UserInfoVo userInfoVo = null;
        Object userInfoObjForAuthToken = redisUtils.strGet(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + headerAuthToken);
        if (userInfoObjForAuthToken == null) {
            // 尝试获取刷新 token
            String headerRememberMeToken = request.getHeader(ymlRememberMeToken);
            if (StringUtils.hasText(headerRememberMeToken)) {
                Object userInfoObjForRememberMeToken = redisUtils.strGet(RedisKeyEnum.GROUP_REMEMBER_ME_TOKEN.getKey() + headerRememberMeToken);
                if (userInfoObjForRememberMeToken == null) {
                    return null;
                }
                userInfoVo = (UserInfoVo) userInfoObjForRememberMeToken;
                // 重新生成一个token
                headerAuthToken = createToken();
                redisUtils.strSet(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + headerAuthToken, userInfoVo, ymlExpireTime);
                return userInfoVo;
            }
            return null;
        } else {
            userInfoVo = (UserInfoVo) userInfoObjForAuthToken;
            Long expire = redisUtils.getExpire(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + headerAuthToken);
            if (expire != null && expire < ymlSurplusRefreshTime) {
                // 刷新token
                redisUtils.strSet(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + headerAuthToken, userInfoVo, ymlExpireTime);
            }
            return userInfoVo;
        }
    }

    /**
     * 只是获取用户信息，不创建Token缓存，不刷新token，不设置响应 response
     *
     * @param request 请求
     * @return -
     */
    public UserInfoVo getUserInfoVo(HttpServletRequest request) {
        String headerAuthToken = request.getHeader(ymlAuthToken);
        if (StringUtils.hasText(headerAuthToken)) {
            return getUserInfoVo(request, headerAuthToken);
        } else {
            // 请求头没有 AuthToken 只有 RememberMeToken 也尽最大可能的尝试获取用户信息，创建新的 token，进行缓存
            String headerRememberMeToken = request.getHeader(ymlRememberMeToken);
            if (StringUtils.hasText(headerRememberMeToken)) {
                Object userInfoObjForRememberMeToken = redisUtils.strGet(RedisKeyEnum.GROUP_REMEMBER_ME_TOKEN.getKey() + headerRememberMeToken);
                if (userInfoObjForRememberMeToken == null) {
                    return null;
                }
                // 重载方法，参数没有HttpServletResponse，所以不创建Token和缓存
                return (UserInfoVo) userInfoObjForRememberMeToken;
            }
            return null;
        }
    }

    public boolean hasAuthToken(HttpServletRequest request) {
        String headerAuthToken = request.getHeader(ymlAuthToken);
        if (StringUtils.hasText(headerAuthToken)) {
            return redisUtils.hasKey(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + headerAuthToken);
        }
        return false;
    }

    public boolean hasRememberMeToken(HttpServletRequest request) {
        String headerRememberMeToken = request.getHeader(ymlRememberMeToken);
        if (StringUtils.hasText(headerRememberMeToken)) {
            return redisUtils.hasKey(RedisKeyEnum.GROUP_REMEMBER_ME_TOKEN.getKey() + headerRememberMeToken);
        }
        return false;
    }

    public void setUserAuthTokenList(Long userId, List<AuthTokenCacheDto> authTokenCacheDtoList) {
        if (userId != null && !ObjectUtils.isEmpty(authTokenCacheDtoList)) {
            redisUtils.strSet(RedisKeyEnum.GROUP_CURRENT_ONLINE_USER.getKey() + userId, authTokenCacheDtoList);
        }
    }

    public List<AuthTokenCacheDto> getUserAllAuthTokenList(Long userId) {
        if (userId != null) {
            Object object = redisUtils.strGet(RedisKeyEnum.GROUP_CURRENT_ONLINE_USER.getKey() + userId);
            if (object instanceof ArrayList<?> objectList) {
                return objectList.stream().map(t -> (AuthTokenCacheDto) t).collect(Collectors.toList());
            }
        }
        return null;
    }

    public Map<Long, List<AuthTokenCacheDto>> getUserAllAuthTokenList(String authToken, String rememberMeToken) {
        UserInfoVo userInfoVo;
        authToken = StringUtils.hasText(authToken) ? authToken : "";
        Object userInfoObjForAuthToken = redisUtils.strGet(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + authToken);
        userInfoVo = userInfoObjForAuthToken instanceof UserInfoVo ? (UserInfoVo) userInfoObjForAuthToken : null;

        if (userInfoVo == null) {
            rememberMeToken = StringUtils.hasText(rememberMeToken) ? rememberMeToken : "";
            Object userInfoObjForRememberMeToken = redisUtils.strGet(RedisKeyEnum.GROUP_REMEMBER_ME_TOKEN.getKey() + rememberMeToken);
            userInfoVo = userInfoObjForRememberMeToken instanceof UserInfoVo ? (UserInfoVo) userInfoObjForRememberMeToken : null;
        }

        if (userInfoVo != null) {
            List<AuthTokenCacheDto> userAllAuthTokenList = getUserAllAuthTokenList(userInfoVo.getUserId());
            if (!ObjectUtils.isEmpty(userAllAuthTokenList)) {
                Map<Long, List<AuthTokenCacheDto>> authTokenMap = new HashMap<>();
                authTokenMap.put(userInfoVo.getUserId(), userAllAuthTokenList);
                return authTokenMap;
            }
        }

        return null;
    }

    public void logout(HttpServletRequest request) {
        logout(request.getHeader(ymlAuthToken), request.getHeader(ymlRememberMeToken));
    }

    public void logout(String authToken, String rememberMeToken) {
        // 当前登录客户端的 总人数
        Map<Long, List<AuthTokenCacheDto>> userAllAuthTokenMap = getUserAllAuthTokenList(authToken, rememberMeToken);
        Set<Long> userIds = userAllAuthTokenMap.keySet();
        Optional<Long> userIdOptional = userIds.stream().findFirst();
        if (userIdOptional.isPresent()) {
            List<AuthTokenCacheDto> userAuthTokenList = userAllAuthTokenMap.get(userIdOptional.get());
            userAuthTokenList = userAuthTokenList.stream().filter(t -> !t.getAuthToken().equals(authToken)).collect(Collectors.toList());
            setUserAuthTokenList(userIdOptional.get(), userAuthTokenList);
        }
        // 当前登录 authToken
        if (StringUtils.hasText(authToken)) {
            redisUtils.del(RedisKeyEnum.GROUP_AUTH_TOKEN.getKey() + authToken);
        }
        // 记住我 rememberMeToken
        if (StringUtils.hasText(rememberMeToken)) {
            redisUtils.del(RedisKeyEnum.GROUP_REMEMBER_ME_TOKEN.getKey() + rememberMeToken);
        }
    }


}
