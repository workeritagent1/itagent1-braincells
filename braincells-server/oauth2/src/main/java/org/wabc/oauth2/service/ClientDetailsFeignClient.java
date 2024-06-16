package org.wabc.oauth2.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wabc.commons.dto.OauthClientDetailsDTO;
import org.wabc.commons.result.ApiResult;

/**
 * Class description.
 *
 * @author wabc
 * @version 1.0
 * @since 2024-06-11
 */
@FeignClient(value = "sysadmin", contextId = "oauth-client")
public interface ClientDetailsFeignClient {

    @GetMapping("/sysadmin/oauthClientDetails/getByClientId")
    ApiResult<OauthClientDetailsDTO> getOAuth2ClientById(@RequestParam String clientId);
}

