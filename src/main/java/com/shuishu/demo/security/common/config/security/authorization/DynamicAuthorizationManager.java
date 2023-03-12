package com.shuishu.demo.security.common.config.security.authorization;


import com.shuishu.demo.security.common.utils.TokenUtils;
import com.shuishu.demo.security.entity.dto.PermissionCacheDto;
import com.shuishu.demo.security.entity.vo.UserInfoVo;
import com.shuishu.demo.security.service.ResourceService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author ：谁书-ss
 * @date ：2023-03-11 0:06
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：动态权限决策管理器
 * <p></p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    private final TokenUtils tokenUtils;
    private final ResourceService resourceService;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        log.info("【DynamicAuthorizationManager 授权管理器】执行verify()方法");
        AuthorizationManager.super.verify(authentication, requestAuthorizationContext);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        log.info("【DynamicAuthorizationManager 授权管理器】执行check()方法");
        // 当前用户 或 系统权限信息 是空数据的时候，或者是有数据并且和当前用户权限匹配的情况下返回true
        boolean isGranted = false;
        // 当前用户的权限信息
        // request
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        // 请求URI
        String requestUri = request.getRequestURI();
        // 当前用户信息
        UserInfoVo userInfoVo = tokenUtils.getUserInfoVo(request);
        // 系统权限信息
        List<PermissionCacheDto> cachePermissionList = resourceService.findCachePermissionList();

        if (ObjectUtils.isEmpty(cachePermissionList)) {
            isGranted = true;
        } else {
            // 系统权限信息
            List<String> isNotAuthorizationUrlList = cachePermissionList.stream()
                    .filter(t -> !Boolean.TRUE.equals(t.getIsNeedAuthorization()))
                    .map(PermissionCacheDto::getPermissionUrl)
                    .toList();

            AntPathMatcher antPathMatcher = new AntPathMatcher();

            if (userInfoVo == null) {
                // 根据URI 校验
                if (ObjectUtils.isEmpty(isNotAuthorizationUrlList)) {
                    isGranted = true;
                } else {
                    // 用户空，那就根据系统放开的权限url 匹配当前匿名用户访问的url
                    for (String url : isNotAuthorizationUrlList) {
                        if (antPathMatcher.match(url, requestUri)) {
                            isGranted = true;
                        }
                    }
                }
            } else {
                // 根据用户授权信息校验
                Collection<? extends GrantedAuthority> authorities = userInfoVo.getAuthorities();
                if (ObjectUtils.isEmpty(authorities)) {
                    isGranted = true;
                } else {
                    for (GrantedAuthority authority : authorities) {
                        if (authority != null &&
                                StringUtils.hasText(authority.getAuthority()) &&
                                antPathMatcher.match(authority.getAuthority(), requestUri)) {
                            isGranted = true;
                        }
                    }
                }
            }
        }

        return new AuthorizationDecision(isGranted);
    }


}
