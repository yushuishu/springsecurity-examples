package com.shuishu.demo.security.enums;


/**
 * @author ：谁书-ss
 * @date ：2023-03-10 22:46
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：Redis 常用缓存的 key
 * <p></p>
 */
public enum RedisKeyEnum {
    /**
     * Redis 常用缓存的 key
     */
    KEY_PERMISSION_URL_LIST("PERMISSION_URL_LIST", "权限URL集合"),
    KEY_CURRENT_ONLINE_NUMBER("CURRENT_ONLINE_NUMBER", "当前在线人数"),

    ;

    private final String key;
    private final String des;

    RedisKeyEnum(String key, String des) {
        this.key = key;
        this.des = des;
    }

    public String getKey() {
        return key;
    }

    public String getDes() {
        return des;
    }
}
