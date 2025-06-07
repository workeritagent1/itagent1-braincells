package ${converterPackage};

import ${entityPackage}.${entity};
import ${dtoPackage}.${entity}DTO;
import ${voPackage}.${entity}VO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * ${table.comment!table.name}对象转换器
 *
 * <p>Entity/DTO/VO 相互转换</p>
 * @author ${author}
 * @date ${date}
 */
@Mapper(componentModel = "spring")
public interface ${entity}Converter {

    /**
     * 单例
     */
    ${entity}Converter INSTANCE = Mappers.getMapper(${entity}Converter.class);

    /**
     * Entity转DTO
     * @param entity 实体
     * @return DTO
     */
    ${entity}DTO entityToDto(${entity} entity);

    /**
     * DTO转Entity
     * @param dto DTO
     * @return 实体
     */
    ${entity} entityFromDto(${entity}DTO dto);

    /**
     * Entity转VO
     * @param entity 实体
     * @return VO
     */
    ${entity}VO entityToVo(${entity} entity);

    /**
     * DTO合并到实体，仅非空字段覆盖
     * @param dto 请求体
     * @param entity 实体
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(${entity}DTO dto, @MappingTarget ${entity} entity);
}