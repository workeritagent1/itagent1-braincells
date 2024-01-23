package org.wabc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wabc.system.entity.SysUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wabc
 * @since 2024-01-02
 */
public interface SysUserService extends IService<SysUser> {
    SysUser loadUserByUsername(String username);
}
