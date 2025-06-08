package org.wabc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wabc.commons.model.PageResult;
import org.wabc.system.dto.SysUserDTO;
import org.wabc.system.dto.SysUserPageDTO;
import org.wabc.system.entity.SysUser;
import org.wabc.system.vo.SysUserVO;

/**
 * 系统用户表业务接口
 *
 * <p>定义系统用户表相关操作</p>
 * @author wabc
 * @date 2025-05-18
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 创建系统用户表
     * @param dto {@link SysUserDTO}
     */
    void create(SysUserDTO dto);

    /**
     * 更新系统用户表
     * @param id 主键
     * @param dto {@link SysUserDTO}
     */
    void update(Long id, SysUserDTO dto);

    /**
     * 删除系统用户表
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param pageDTO 分页条件
     * @return 分页结果VO
     */
    PageResult<SysUserVO> page(SysUserPageDTO pageDTO);

    /**
     * 详情
     * @param id 主键
     * @return VO
     */
    SysUserVO detail(Long id);

}