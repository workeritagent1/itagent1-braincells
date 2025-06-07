package org.wabc.system.converter;

import org.wabc.system.entity.SysRole;
import org.wabc.system.dto.SysRoleDTO;
import org.wabc.system.vo.SysRoleVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 角色表对象转换器
 *
 * <p>Entity/DTO/VO 相互转换</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper(componentModel = "spring")
public interface SysRoleConverter {

    /**
     * 单例
     */
    SysRoleConverter INSTANCE = Mappers.getMapper(SysRoleConverter.class);

    /**
     * Entity转DTO
     * @param entity 实体
     * @return DTO
     */
    SysRoleDTO entityToDto(SysRole entity);

    /**
     * DTO转Entity
     * @param dto DTO
     * @return 实体
     */
    SysRole entityFromDto(SysRoleDTO dto);

    /**
     * Entity转VO
     * @param entity 实体
     * @return VO
     */
    SysRoleVO entityToVo(SysRole entity);

    /**
     * DTO合并到实体，仅非空字段覆盖
     * @param dto 请求体
     * @param entity 实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(SysRoleDTO dto, @MappingTarget SysRole entity);
}