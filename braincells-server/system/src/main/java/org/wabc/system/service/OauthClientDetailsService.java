package org.wabc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wabc.system.entity.OauthClientDetails;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wabc
 * @version 1.0
 * @since 2023-12-31
 */
public interface OauthClientDetailsService extends IService<OauthClientDetails> {

    OauthClientDetails loadClientByClientId(String clientId);
}
