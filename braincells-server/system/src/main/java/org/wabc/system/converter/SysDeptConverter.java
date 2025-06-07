package org.wabc.system.converter;

import org.wabc.system.entity.SysDept;
import org.wabc.system.dto.SysDeptDTO;
import org.wabc.system.vo.SysDeptVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 部门表对象转换器
 *
 * <p>Entity/DTO/VO 相互转换</p>
 * @author wabc
 * @date 2025-05-18
 */
@Mapper(componentModel = "spring")
public interface SysDeptConverter {

    /**
     * 单例
     */
    SysDeptConverter INSTANCE = Mappers.getMapper(SysDeptConverter.class);

    /**
     * Entity转DTO
     * @param entity 实体
     * @return DTO
     */
    SysDeptDTO entityToDto(SysDept entity);

    /**
     * DTO转Entity
     * @param dto DTO
     * @return 实体
     */
    SysDept entityFromDto(SysDeptDTO dto);

    /**
     * Entity转VO
     * @param entity 实体
     * @return VO
     */
    SysDeptVO entityToVo(SysDept entity);

    /**
     * DTO合并到实体，仅非空字段覆盖
     * @param dto 请求体
     * @param entity 实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(SysDeptDTO dto, @MappingTarget SysDept entity);
}