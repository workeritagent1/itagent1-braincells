package org.wabc.commons.gateway.service;

import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.wabc.commons.gateway.result.Result;

/**
 * Class description.
 *
 * @author wabc
 * @version 1.0
 * @since 2024-01-06
 */
@Service
public class SysPermissionService {
    // http://localhost:10000/gateway/system/sysPermission/loadPermissionRoles
    @Value("${security.permissionUrl}")
    private  String permissionUrl;

    @Autowired
    RestTemplate restTemplate;

    public Result<String> loadPermissionRoles(){
        String str = restTemplate.getForObject(permissionUrl, String.class);
        Result result = JSONUtil.toBean(str,Result.class);
        return result;
    }
}