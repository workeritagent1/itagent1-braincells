package org.wabc.authorization.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.wabc.authorization.entity.OauthClientDetails;
import org.wabc.authorization.entity.SysUser;
import org.wabc.authorization.result.Result;

/**
 * Class description.
 *  在Spring Cloud中，FeignClient默认是不会经过网关的。
 *
 * @author wabc
 * @version 1.0
 * @since 2024-01-06
 */
@FeignClient(name = "system")
public interface SystemServiceClient {
    @GetMapping("/sysUser/loadUserByUsername/{username}")
    Result<SysUser> loadUserByUsername(@PathVariable String username);

    @GetMapping("/oauthClientDetails/loadClientByClientId/{clientId}")
    Result<OauthClientDetails> loadClientByClientId(@PathVariable String clientId);
}