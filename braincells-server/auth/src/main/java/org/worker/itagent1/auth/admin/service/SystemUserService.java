package org.worker.itagent1.auth.admin.service;

import org.worker.itagent1.auth.admin.entity.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author itagent1.worker
 * @since 2023-06-05 10:58:42
 */
public interface SystemUserService extends IService<SystemUser> {
    SystemUser getUserByUsername(String username);
}
