package org.worker.itagent1.auth.admin.service.impl;

import org.worker.itagent1.auth.admin.entity.SystemUser;
import org.worker.itagent1.auth.admin.dao.SystemUserMapper;
import org.worker.itagent1.auth.admin.service.SystemUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itagent1.worker
 * @since 2023-06-05 10:58:42
 */
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser> implements SystemUserService {

    @Override
    public SystemUser getUserByUsername(String username) {
        return this.baseMapper.getUserByUserName(username);
    }
}
