package org.wabc.commons.authorization.config;

/**
 * Class description.
 *
 * @author wabc
 * @version 1.0
 * @since 2024-03-29
 */

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.wabc.commons.authorization.model.SysUserDetails;

import java.util.HashMap;
import java.util.Map;


/**
 * 自定义token增强器，作用可以在jwt中添加自定义信息
 *  <a href="https://github.com/torlesse-liang/torlesse-oauth2">参考</a>
 * @author wabc
 * @version 1.0
 * @since 2024-04-01
 */
@Slf4j
public class CustomTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Authentication userAuthentication = authentication.getUserAuthentication();
        if (userAuthentication != null) {
            log.info("principal:{}", JSONUtil.toJsonStr(userAuthentication.getPrincipal()));
            // 把用户标识以userDetails这个Key加入到JWT的额外信息中去
            Map<String, Object> additionalInfo = new HashMap<>();
            SysUserDetails userDetails = (SysUserDetails) userAuthentication.getPrincipal();
            additionalInfo.put("id", userDetails.getId());
            additionalInfo.put("username", userDetails.getUsername());
            additionalInfo.put("deptId", userDetails.getDeptId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        }else{
            log.error("userAuthentication is null ");
        }
        return accessToken;
    }
}