package org.wabc.commons.security;


import org.apache.commons.codec.digest.HmacUtils;


/**
 * InternalApiSigner
 * -----------------
 * HMAC-SHA256 签名生成与明文拼接工具，保证前后端一致。
 */
public class InternalApiSigner {

    /**
     * 构建待签名字符串
     * @param method HTTP方法（GET, POST等）
     * @param url 请求的URL（不包括协议、域名和端口，仅路径和查询参数）
     * @param timestamp 时间戳
     * @param nonce 随机数
     * @param body 请求体（对于GET请求，可以为null或空）
     * @return 待签名字符串
     */
    public static String buildDataToSign(String method, String url, long timestamp, String nonce, String body) {
        System.out.println("method===>" +method);
        System.out.println("url===>" +url);
        System.out.println("timestamp===>" +timestamp);
        System.out.println("nonce===>" +nonce);
        System.out.println("body===>" +body);

        StringBuilder sb = new StringBuilder();
        sb.append(method).append(":").append(url)
                .append(":").append(timestamp)
                .append(":").append(nonce);
        // 如果有body，则追加
        if (body != null && !body.isEmpty()) {
            sb.append(":").append(body);
        }
        return sb.toString();
    }

    /**
     * 使用HMAC-SHA256算法和密钥生成签名
     * @param data 待签名字符串
     * @param secretKey 密钥
     * @return 签名
     */
    public static String sign(String data, String secretKey) {
        return HmacUtils.hmacSha256Hex(secretKey, data);
    }
}