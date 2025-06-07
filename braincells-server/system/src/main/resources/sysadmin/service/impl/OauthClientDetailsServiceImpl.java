package sysadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.wabc.sysadmin.models.entity.OauthClientDetails;
import org.wabc.sysadmin.mapper.OauthClientDetailsMapper;
import org.wabc.sysadmin.service.OauthClientDetailsService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wabc
 * @since 2023-12-31
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements OauthClientDetailsService {
}