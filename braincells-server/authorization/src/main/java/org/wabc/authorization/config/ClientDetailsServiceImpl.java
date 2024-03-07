package org.wabc.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.wabc.authorization.entity.OauthClientDetails;
import org.wabc.authorization.result.Result;
import org.wabc.authorization.service.SystemServiceClient;

/**
 * 管理客户端应用程序的信息和凭据
 *
 * @author wabc
 * @version 1.0
 * @since 2023-12-31
 */
@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    SystemServiceClient systemServiceClient;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        Result<OauthClientDetails> result = systemServiceClient.loadClientByClientId(clientId);
        OauthClientDetails oauthClient = result.getData();
        oauthClient = new OauthClientDetails();
        oauthClient.setClientId("client_id_01");
        oauthClient.setClientSecret("$2a$10$OlspvduHdbBzm5OenfeaAeSFMFKOXtq6mGzPwjaN/seQqio7pcFEG");
        oauthClient.setScope("all");
        oauthClient.setAccessTokenValidity(3600);
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
        clientDetails.setAccessTokenValiditySeconds(oauthClient.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(oauthClient.getRefreshTokenValidity());
        return clientDetails;
    }
}
