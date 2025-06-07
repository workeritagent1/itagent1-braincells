package sysadmin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wabc.commons.dto.SysUserDTO;
import org.wabc.sysadmin.models.entity.SysUser;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wabc
 * @since 2024-01-02
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 根据用户名获取认证信息
     *
     * @param username
     * @return
     */
    SysUserDTO getAuthInfoByUsername(String username);
}
