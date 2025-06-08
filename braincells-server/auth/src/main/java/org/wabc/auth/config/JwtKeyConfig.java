package org.wabc.auth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class JwtKeyConfig {
    private static final Logger log = LoggerFactory.getLogger(JwtKeyConfig.class);

    @Value("${jwt.key.private}")
    private String privateKey;

    @Value("${jwt.key.public}")
    private String publicKey;

    @Bean
    public KeyPair keyPair() {
        try {
            // 去除PEM头尾和换行
            String privateKeyPem = privateKey
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll("\\s+", "");
            String publicKeyPem = publicKey
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s+", "");

            log.info("===privateKeyPem====");
            log.info(privateKeyPem);
            log.info("===publicKeyPem====");
            log.info(publicKeyPem);

            // 转成key字节流
            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyPem);
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyPem);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(publicKeyBytes);

            RSAPrivateKey privateKeyObj = (RSAPrivateKey) keyFactory.generatePrivate(privateSpec);
            RSAPublicKey publicKeyObj = (RSAPublicKey) keyFactory.generatePublic(publicSpec);

            return new KeyPair(publicKeyObj, privateKeyObj);
        } catch (Exception e) {
            throw new RuntimeException("加载JWT密钥对失败", e);
        }
    }

    // JWKSet端点 /oauth2/jwks 获取授权服务器公钥（JWKS）用于JWT验签
    // http://localhost:10001/auth/oauth2/jwks

    /**
     * Spring Authorization Server 会自动实现 /oauth2/jwks 端点，
     * 无需手动创建控制器。端点会返回 JWK Set（JSON Web Key Set），包含授权服务器的公钥信息。
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource(KeyPair keyPair) {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID("jwt")
                .build();
        return new ImmutableJWKSet<>(new JWKSet(rsaKey));
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}