package org.wabc.authorization.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;
import org.wabc.authorization.entity.OauthClientDetails;
import org.wabc.authorization.mapper.OauthClientDetailsMapper;

import java.util.List;

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
    OauthClientDetailsMapper mapper;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        QueryWrapper<OauthClientDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id", clientId);
        List<OauthClientDetails> clients = mapper.selectList(queryWrapper);
        OauthClientDetails oauthClient = !clients.isEmpty() ? clients.get(0) : null;

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

        clientDetails.setClientSecret(oauthClient.getClientSecret());
        clientDetails.setAccessTokenValiditySeconds(oauthClient.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(oauthClient.getRefreshTokenValidity());

        return clientDetails;
    }

}
