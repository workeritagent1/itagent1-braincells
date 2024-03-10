package org.wabc.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.wabc.authorization.entity.OauthClientDetails;
import org.wabc.authorization.service.SystemServiceClient;

/**
 * 管理客户端应用程序的信息和凭据
 *
 * @author wabc
 * @version 1.0
 * @since 2023-12-31
 */
@Service("ClientDetailsServiceImpl")
public class ClientDetailsServiceImpl  implements ClientDetailsService{
    @Autowired
    SystemServiceClient systemServiceClient;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
//        Result<OauthClientDetails> result = systemServiceClient.loadClientByClientId(clientId);
        OauthClientDetails oauthClient =  new OauthClientDetails();
        oauthClient.setClientId("client_id_01");
//        oauthClient.setClientSecret("123456");
        oauthClient.setClientSecret("$2a$10$yZAgQ2DUIQAFJvZcmfBc5.dBplLAWcr.oYwaLnFVwj3xNiHng3PK.");
        oauthClient.setScope("all");
        oauthClient.setAuthorizedGrantTypes("authorization_code,implicit,password,client_credentials,refresh_token");
        oauthClient.setAccessTokenValidity(3600);
        oauthClient.setRefreshTokenValidity(86400);
        oauthClient.setAutoapprove("false");
        if (oauthClient == null) {
            throw new ClientRegistrationException("Client not found: " + clientId);
        }

        BaseClientDetails clientDetails = new BaseClientDetails(
                oauthClient.getClientId(),
                oauthClient.getResourceIds(),
                oauthClient.getScope(),
                oauthClient.getAuthorizedGrantTypes(),
                oauthClient.getAuthorities(),
                oauthClient.getWebServerRedirectUri()
        );
//        clientDetails.setClientSecret(oauthClient.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(oauthClient.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(oauthClient.getRefreshTokenValidity());
        return clientDetails;
    }

}
