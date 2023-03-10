package com.shuishu.demo.security.common.utils;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

import java.nio.charset.StandardCharsets;

/**
 * @author ：谁书-ss
 * @date ：2023-03-09 23:54
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：加解密 工具
 * <p></p>
 */
public class PasswordUtils {

    /**
     * @param key             自定义密钥
     * @param encryptPassword 待加密的字符
     * @return 加密后的值
     */
    public static String encrypt(String key, String encryptPassword) {
        // 生成密钥
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        // 在密钥生成时必须为128/192/256 bits（位）， byte一字节8位，需要达到256位，需要32字节
        if (bytes.length != 32) {
            //创建32字节的byte数组
            byte[] b = new byte[32];
            if (bytes.length < 32) {
                //将自定义密钥添加到b数组
                /**
                 * 方法：System.arraycopy
                 * 参数：
                 * src：the source array要插入的数组
                 * srcPos：starting position in the source array插入数组的起始位置
                 * dest：the destination array被插入的数组
                 * destPos：starting position in the destination data被插入数组插入时的起始位置
                 * length：the number of array elements to be copied要插入的数组的长度
                 */
                System.arraycopy(bytes, 0, b, 0, bytes.length);
            }
            bytes = b;
        }
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, bytes);
        //加密为16进制表示
        return aes.encryptHex(encryptPassword);
    }


    /**
     * key为自定义密钥
     *
     * @param key             自定义密钥
     * @param decryptPassword 待解密的值
     * @return 解密后的值
     */
    public static String decrypt(String key, String decryptPassword) {
        // 生成密钥
        byte[] bytes = key.getBytes(StandardCharsets.UTF_8);
        if (bytes.length != 32) {
            byte[] b = new byte[32];
            if (bytes.length < 32) {
                System.arraycopy(bytes, 0, b, 0, bytes.length);
            }
            bytes = b;
        }
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, bytes);
        //解密为字符串
        return aes.decryptStr(decryptPassword, CharsetUtil.CHARSET_UTF_8);
    }


}
