package org.wabc.authorization.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.wabc.authorization.service.fallback.UserFeignFallbackClient;
import org.wabc.commons.dto.SysUserDTO;
import org.wabc.commons.result.ApiResult;

@FeignClient(value = "sysadmin", fallback = UserFeignFallbackClient.class)
public interface UserFeignClient {

    @GetMapping("/sysadmin/sysUser/username/{username}")
    ApiResult<SysUserDTO> getUsername(@PathVariable String username);
}
