package org.wabc.commons.util;


import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;

/**
 * 内部服务 AccessKey/SecretKey 生成工具
 */
public class KeyGenUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成 AccessKey（16字节的安全Base64-URL，无等号，无+号，无斜杠；或用UUID去掉短横线）
     */
    public static String generateAccessKey() {
        byte[] bytes = new byte[16]; // 128位
        RANDOM.nextBytes(bytes);
        return Base64.encodeBase64URLSafeString(bytes); // 简洁安全
        // 或者 return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成 SecretKey（32字节的安全Base64-URL，256bit）
     */
    public static String generateSecretKey() {
        byte[] bytes = new byte[32]; // 256位
        RANDOM.nextBytes(bytes);
        return Base64.encodeBase64URLSafeString(bytes); // 更推荐
    }

    public static void main(String[] args) {
        System.out.println("AccessKey: " + generateAccessKey());
        System.out.println("SecretKey: " + generateSecretKey());
    }
}