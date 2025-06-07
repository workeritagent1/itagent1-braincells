package org.wabc.system.converter;

import org.wabc.system.entity.SysUserRole;
import org.wabc.system.dto.SysUserRoleDTO;
import org.wabc.system.vo.SysUserRoleVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 用户与角色关联表对象转换器
 *
 * <p>Entity/DTO/VO 相互转换</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper(componentModel = "spring")
public interface SysUserRoleConverter {

    /**
     * 单例
     */
    SysUserRoleConverter INSTANCE = Mappers.getMapper(SysUserRoleConverter.class);

    /**
     * Entity转DTO
     * @param entity 实体
     * @return DTO
     */
    SysUserRoleDTO entityToDto(SysUserRole entity);

    /**
     * DTO转Entity
     * @param dto DTO
     * @return 实体
     */
    SysUserRole entityFromDto(SysUserRoleDTO dto);

    /**
     * Entity转VO
     * @param entity 实体
     * @return VO
     */
    SysUserRoleVO entityToVo(SysUserRole entity);

    /**
     * DTO合并到实体，仅非空字段覆盖
     * @param dto 请求体
     * @param entity 实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(SysUserRoleDTO dto, @MappingTarget SysUserRole entity);
}