package org.wabc.system.converter;

import org.wabc.system.entity.SysRoleMenu;
import org.wabc.system.dto.SysRoleMenuDTO;
import org.wabc.system.vo.SysRoleMenuVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 角色与权限关联表对象转换器
 *
 * <p>Entity/DTO/VO 相互转换</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper(componentModel = "spring")
public interface SysRoleMenuConverter {

    /**
     * 单例
     */
    SysRoleMenuConverter INSTANCE = Mappers.getMapper(SysRoleMenuConverter.class);

    /**
     * Entity转DTO
     * @param entity 实体
     * @return DTO
     */
    SysRoleMenuDTO entityToDto(SysRoleMenu entity);

    /**
     * DTO转Entity
     * @param dto DTO
     * @return 实体
     */
    SysRoleMenu entityFromDto(SysRoleMenuDTO dto);

    /**
     * Entity转VO
     * @param entity 实体
     * @return VO
     */
    SysRoleMenuVO entityToVo(SysRoleMenu entity);

    /**
     * DTO合并到实体，仅非空字段覆盖
     * @param dto 请求体
     * @param entity 实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(SysRoleMenuDTO dto, @MappingTarget SysRoleMenu entity);
}