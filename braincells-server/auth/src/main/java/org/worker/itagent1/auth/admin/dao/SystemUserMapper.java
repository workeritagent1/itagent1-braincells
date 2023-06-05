package org.worker.itagent1.auth.admin.dao;

import org.worker.itagent1.auth.admin.entity.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itagent1.worker
 * @since 2023-06-05 10:58:42
 */
@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {
    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    SystemUser getUserByUserName(String username);
}
