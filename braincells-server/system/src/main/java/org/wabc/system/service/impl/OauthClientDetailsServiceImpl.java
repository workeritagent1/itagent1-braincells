package org.wabc.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wabc.system.entity.OauthClientDetails;
import org.wabc.system.mapper.OauthClientDetailsMapper;
import org.wabc.system.service.OauthClientDetailsService;

import java.util.List;

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
    @Autowired
    OauthClientDetailsMapper mapper;
    @Override
    public OauthClientDetails loadClientByClientId(String clientId) {
        QueryWrapper<OauthClientDetails> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("clientId", clientId);
        List<OauthClientDetails> clients = mapper.selectList(queryWrapper);
        return !clients.isEmpty() ? clients.get(0) : null;
    }
}
