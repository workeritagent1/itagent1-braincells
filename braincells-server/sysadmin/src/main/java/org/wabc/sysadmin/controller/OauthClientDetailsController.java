package org.wabc.system.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.wabc.commons.dto.OauthClientDetailsDTO;
import org.wabc.commons.result.ApiResult;
import org.wabc.system.models.entity.OauthClientDetails;
import org.wabc.system.service.OauthClientDetailsService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@RestController
@RequestMapping("/oauthClientDetails")
public class OauthClientDetailsController {
    @Autowired
    OauthClientDetailsService oauthClientDetailsService;

    // url中不暴露参数
    @Operation(summary = "获取OAuth2客户端认证信息", description = "Feign调用")
    @GetMapping("/getByClientId")
    public ApiResult<OauthClientDetailsDTO> getOAuth2ClientByClientId(
            @Parameter(name = "clientId", description = "客户端ID", required = true)
            @RequestParam String clientId) {
        OauthClientDetails client = oauthClientDetailsService.getById(clientId);
        Assert.isTrue(client != null, "OAuth2客户端不存在");
        OauthClientDetailsDTO clientDTO = new OauthClientDetailsDTO();
        BeanUtil.copyProperties(client, clientDTO);
        return ApiResult.success(clientDTO);
    }
}

