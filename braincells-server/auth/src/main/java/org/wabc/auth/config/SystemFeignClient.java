package org.wabc.auth.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.wabc.commons.model.Result;

@FeignClient(name = "system")
public interface SystemFeignClient {
    @GetMapping("/system/sys-users/auth-user-info")
    Result<String> getByUsername(@RequestParam("username") String username);

}
