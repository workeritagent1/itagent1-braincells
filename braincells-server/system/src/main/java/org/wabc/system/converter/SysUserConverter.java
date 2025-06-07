package org.wabc.system.converter;

import org.wabc.system.entity.SysUser;
import org.wabc.system.dto.SysUserDTO;
import org.wabc.system.vo.SysUserVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 系统用户表对象转换器
 *
 * <p>Entity/DTO/VO 相互转换</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper(componentModel = "spring")
public interface SysUserConverter {

    /**
     * 单例
     */
    SysUserConverter INSTANCE = Mappers.getMapper(SysUserConverter.class);

    /**
     * Entity转DTO
     * @param entity 实体
     * @return DTO
     */
    SysUserDTO entityToDto(SysUser entity);

    /**
     * DTO转Entity
     * @param dto DTO
     * @return 实体
     */
    SysUser entityFromDto(SysUserDTO dto);

    /**
     * Entity转VO
     * @param entity 实体
     * @return VO
     */
    SysUserVO entityToVo(SysUser entity);

    /**
     * DTO合并到实体，仅非空字段覆盖
     * @param dto 请求体
     * @param entity 实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(SysUserDTO dto, @MappingTarget SysUser entity);
}