package org.wabc.auth;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        String s = new BCryptPasswordEncoder().encode("secret");
        System.out.println(s);
        // 用户登录时提供的明文密码与数据库中存储的已加密密码进行匹配。
        boolean  equivalent = new BCryptPasswordEncoder().matches("secret", s);
        System.out.printf("");
    }
}
