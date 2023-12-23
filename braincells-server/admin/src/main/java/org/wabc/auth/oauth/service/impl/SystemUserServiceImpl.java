package org.wabc.auth.oauth.service.impl;

import org.wabc.auth.oauth.entity.SystemUser;
import org.wabc.auth.oauth.dao.SystemUserMapper;
import org.wabc.auth.oauth.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itagent1.worker
 * @since 2023-06-05 10:55:08
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

}
