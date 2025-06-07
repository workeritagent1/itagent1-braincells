package org.wabc.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.wabc.system.entity.SysDept;
import org.wabc.system.dto.SysDeptDTO;
import org.wabc.system.dto.SysDeptPageDTO;
import org.wabc.system.vo.SysDeptVO;
import org.wabc.commons.model.PageResult;

/**
 * 部门表业务接口
 *
 * <p>定义部门表相关操作</p>
 * @author wabc
 * @date 2025-05-18
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 创建部门表
     * @param dto {@link SysDeptDTO}
     */
    void create(SysDeptDTO dto);

    /**
     * 更新部门表
     * @param id 主键
     * @param dto {@link SysDeptDTO}
     */
    void update(Long id, SysDeptDTO dto);

    /**
     * 删除部门表
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param pageDTO 分页条件
     * @return 分页结果VO
     */
    PageResult<SysDeptVO> page(SysDeptPageDTO pageDTO);

    /**
     * 详情
     * @param id 主键
     * @return VO
     */
    SysDeptVO detail(Long id);
}