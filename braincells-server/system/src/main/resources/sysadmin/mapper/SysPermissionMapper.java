package sysadmin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.wabc.sysadmin.models.entity.SysPermission;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

//    /**
//     * 获取权限分页列表
//     *
//     * @param page
//     * @param queryParams
//     * @return
//     */
//    List<PermPageVO> listPermPages(Page<PermPageVO> page, PermPageQuery queryParams);

}
