package org.wabc.system.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.wabc.system.entity.OauthClientDetails;
import org.wabc.system.result.Result;
import org.wabc.system.service.OauthClientDetailsService;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Controller
@RequestMapping("/oauthClientDetails")
public class OauthClientDetailsController {
    @Autowired
    OauthClientDetailsService oauthClientDetailsService;

    @GetMapping("/loadClientByClientId/{clientId}")
    Result<OauthClientDetails> loadClientByClientId(@PathVariable String clientId) {
        OauthClientDetails oauthClientDetails = oauthClientDetailsService.loadClientByClientId(clientId);
        return Result.success(oauthClientDetails);
    }
}

