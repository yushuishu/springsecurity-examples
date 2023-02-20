package com.shuishu.demo.security.enums;


import org.springframework.util.StringUtils;

/**
 * @author ：谁书-ss
 * @date ：2023-01-01 15:28
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @Description ：
 */
public interface UserEnum {
    enum AuthType{
        /**
         * 登录类型
         */
        LOCAL("LOCAL", "本系统账号"),
        PHONE("PHONE", "手机号"),
        QQ("QQ", "QQ号"),
        QQ_EMAIL("QQ_EMAIL", "QQ邮箱"),
        ZFB("ZFB", "支付宝"),
        GITHUB("GITHUB", "GitHub号"),
        GOOGLE("GOOGLE", "Google邮箱号"),
        ;

        private final String type;

        private final String desc;

        public static boolean verifyType(String type){
            if (StringUtils.hasText(type)){
                for (AuthType value : values()) {
                    if (value.type.equals(type)){
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
}
