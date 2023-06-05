package org.worker.itagent1.auth.oauth.service.impl;

import org.worker.itagent1.auth.oauth.entity.OauthClientToken;
import org.worker.itagent1.auth.oauth.dao.OauthClientTokenMapper;
import org.worker.itagent1.auth.oauth.service.OauthClientTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itagent1.worker
 * @since 2023-06-04 09:49:52
 */
@Service
public class OauthClientTokenServiceImpl extends ServiceImpl<OauthClientTokenMapper, OauthClientToken> implements OauthClientTokenService {

}
