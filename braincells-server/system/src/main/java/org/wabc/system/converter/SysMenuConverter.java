package org.wabc.system.converter;

import org.wabc.system.entity.SysMenu;
import org.wabc.system.dto.SysMenuDTO;
import org.wabc.system.vo.SysMenuVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 菜单权限表对象转换器
 *
 * <p>Entity/DTO/VO 相互转换</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper(componentModel = "spring")
public interface SysMenuConverter {

    /**
     * 单例
     */
    SysMenuConverter INSTANCE = Mappers.getMapper(SysMenuConverter.class);

    /**
     * Entity转DTO
     * @param entity 实体
     * @return DTO
     */
    SysMenuDTO entityToDto(SysMenu entity);

    /**
     * DTO转Entity
     * @param dto DTO
     * @return 实体
     */
    SysMenu entityFromDto(SysMenuDTO dto);

    /**
     * Entity转VO
     * @param entity 实体
     * @return VO
     */
    SysMenuVO entityToVo(SysMenu entity);

    /**
     * DTO合并到实体，仅非空字段覆盖
     * @param dto 请求体
     * @param entity 实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(SysMenuDTO dto, @MappingTarget SysMenu entity);
}