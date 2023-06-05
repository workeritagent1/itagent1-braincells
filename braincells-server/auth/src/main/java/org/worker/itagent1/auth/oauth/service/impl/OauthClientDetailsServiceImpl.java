package org.worker.itagent1.auth.oauth.service.impl;

import org.worker.itagent1.auth.oauth.entity.OauthClientDetails;
import org.worker.itagent1.auth.oauth.dao.OauthClientDetailsMapper;
import org.worker.itagent1.auth.oauth.service.OauthClientDetailsService;
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
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {

}
