package org.wabc.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.wabc.gateway.result.Result;

/**
 * Class description.
 *  在Spring Cloud中，FeignClient默认是不会经过网关的。
 *
 * @author wabc
 * @version 1.0
 * @since 2024-01-06
 */
@FeignClient(name = "system")
public interface SysPermissionServiceClient {
    @GetMapping("/sysPermission/loadPermissionRoles")
    Result<String> loadPermissionRoles();
}