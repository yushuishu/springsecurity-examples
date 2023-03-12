package com.shuishu.demo.security.enums;


import com.shuishu.demo.security.common.config.security.LoginPolicyConfig;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:28
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 */
public interface UserEnum {
    enum AuthType {
        /**
         * 登录类型
         */
        LOCAL("LOCAL", "本系统账号"),
        PHONE("PHONE", "手机号"),
        EMAIL("EMAIL", "邮箱"),
        QQ("QQ", "QQ号（OAuth）"),
        WE_CHAT("WE_CHAT", "微信（OAuth）"),
        GITHUB("GITHUB", "GitHub（OAuth）"),
        GOOGLE("GOOGLE", "Google（OAuth）"),
        ;

        private final String type;

        private final String desc;

        public static boolean verifyType(String type) {
            if (StringUtils.hasText(type)) {
                for (AuthType value : values()) {
                    if (value.type.equals(type)) {
                        return true;
                    }
                }
            }
            return false;
        }


        AuthType(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }

    enum LoginPolicy {
        /**
         * 登录策略：
         * ONE：达到最大客户端登录人数，将最早登录的客户端给踢掉（默认登录策略配置：{@link LoginPolicyConfig#getLoginPolicy()}）
         * TWO：达到最大客户端登录人数，不允许登录
         */
        ONE, TWO;

        public static boolean existLoginPolicy(String loginPolicy) {
            if (StringUtils.hasText(loginPolicy)) {
                return Arrays.stream(values()).anyMatch(t -> t.name().equals(loginPolicy));
            }
            return false;
        }

    }

}
