package org.wabc.commons.gateway.auth;

import cn.hutool.core.convert.Convert;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName CommonTest
 * @description:
 * @date 2023/12/20 22:24
 */
public class CommonTest {
    @Test
    public void BCryptEncode() {
        String s = new BCryptPasswordEncoder().encode("123456");
        System.out.println(s);
        // 用户登录时提供的明文密码与数据库中存储的已加密密码进行匹配。
        boolean  equivalent = new BCryptPasswordEncoder().matches("123456", "$10$ExMtq3Z1FPp6nD62uf//AuDXLU.OYHP5AOtvX14WzeiiCM2kVld3.");
//        boolean  equivalent = new BCryptPasswordEncoder().matches("123456", s);

        System.out.print(equivalent);
    }
    @Test
    public void listStr() {
        List<String> list = new ArrayList<>();
        list.add("ADMIN");
        list.add("AI");
        list.add("VIDEO");
        System.out.println(list.toString());
//        List<String> roles = Convert.toList(String.class, list.toString());
        List<String> roles = Convert.toList(String.class, "[ADMIN,AI,VIDEO]");
        System.out.println(roles.toString());
    }

    // authorization_code,implicit,password,client_credentials,refresh_token
    @Test
    public void authorization_code() {
        // 授权码模式：获取授权码
        //统一身份中心地址
        String serverUrl = "http://localhost:8001/authorization/oauth/token";
        //身份中心分发的clientId
        String client_id = "";
        //回调地址，登录成功后会将code传递给该地址
        String redirect_uri = "ip:port/receive";

        StringBuilder url = new StringBuilder();

        url.append(serverUrl).append("/oauth2.0/authorize");
        url.append("?response_type=code&client_id=");
        url.append(client_id);
        url.append("&redirect_uri=");
//        url.append(getEncodeUrl(redirect_uri));
//
//        response.sendRedirect(url.toString());
    }
}
