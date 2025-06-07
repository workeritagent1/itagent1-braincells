package org.wabc.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.wabc.system.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门表 Mapper接口
 * <p>对数据库表sys_dept的CURD操作</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {
}