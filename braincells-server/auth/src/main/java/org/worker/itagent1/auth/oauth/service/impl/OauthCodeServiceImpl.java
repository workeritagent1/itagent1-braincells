package org.worker.itagent1.auth.oauth.service.impl;

import org.worker.itagent1.auth.oauth.entity.OauthCode;
import org.worker.itagent1.auth.oauth.dao.OauthCodeMapper;
import org.worker.itagent1.auth.oauth.service.OauthCodeService;
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
public class OauthCodeServiceImpl extends ServiceImpl<OauthCodeMapper, OauthCode> implements OauthCodeService {

}
