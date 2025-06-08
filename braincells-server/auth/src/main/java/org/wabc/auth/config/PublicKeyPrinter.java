package org.wabc.auth.config;

import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

/**
 * 验证 http://localhost:10001/auth/oauth2/jwks的值 n e
 *
 */
@Component
public class PublicKeyPrinter {

    private final KeyPair keyPair;

    public PublicKeyPrinter(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    @PostConstruct
    public void printPublicKeyInfo() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 用RSAKey生成和JWK一致的n,e
        RSAKey rsaKey = new RSAKey.Builder(publicKey).keyID("test").build();
        System.out.println("n = " + rsaKey.getModulus().toString());  // 大整数
        System.out.println("n.base64url = " + rsaKey.getModulus().toString()); // 实际JWK里的内容。注意JWK对象有getModulus()等
        System.out.println("e = " + rsaKey.getPublicExponent().toString());
        System.out.println("e.base64url = " + rsaKey.getPublicExponent().toString());
    }
}