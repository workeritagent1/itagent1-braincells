package org.wabc.auth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.wabc.auth.service.fallback.UserDetailsFeignFallbackClient;
import org.wabc.commons.dto.SysUserDTO;
import org.wabc.commons.result.ApiResult;

@FeignClient(value = "sysadmin", fallback = UserDetailsFeignFallbackClient.class)
public interface UserDetailsFeignClient {

    @GetMapping("/sysadmin/sysUser/username/{username}")
    ApiResult<SysUserDTO> getByUsername(@PathVariable String username);
}
