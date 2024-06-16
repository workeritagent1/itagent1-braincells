package org.wabc.authorization.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.wabc.commons.dto.OauthClientDetailsDTO;
import org.wabc.commons.result.ApiResult;

/**
 * 管理客户端应用程序的信息和凭据
 *
 * @author wabc
 * @version 1.0
 * @since 2023-12-31
 */
@Service("ClientDetailsServiceImpl")
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Autowired
    ClientDetailsFeignClient clientDetailsFeignClient;
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

        ApiResult<OauthClientDetailsDTO> result = clientDetailsFeignClient.getOAuth2ClientById(clientId);

        OauthClientDetailsDTO detailsDTO = result.getData();
        if (detailsDTO == null) {
            throw new ClientRegistrationException("Client not found: " + clientId);
        }

        BaseClientDetails clientDetails = new BaseClientDetails(
                detailsDTO.getClientId(),
                detailsDTO.getResourceIds(),
                detailsDTO.getScope(),
                detailsDTO.getAuthorizedGrantTypes(),
                detailsDTO.getAuthorities(),
                detailsDTO.getWebServerRedirectUri()
        );

        clientDetails.setClientSecret(detailsDTO.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(detailsDTO.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(detailsDTO.getRefreshTokenValidity());

        return clientDetails;
    }

}
