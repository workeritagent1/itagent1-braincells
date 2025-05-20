package ${servicePackage};

import com.baomidou.mybatisplus.extension.service.IService;
import ${entityPackage}.${entity};
import ${dtoPackage}.${entity}DTO;
import ${dtoPackage}.${entity}PageDTO;
import ${voPackage}.${entity}VO;
import org.wabc.commons.model.PageResult;

/**
 * ${table.comment!table.name}业务接口
 *
 * <p>定义${table.comment!table.name}相关操作</p>
 * @author ${author}
 * @date ${date}
 */
public interface ${entity}Service extends IService<${entity}> {

    /**
     * 创建${table.comment!table.name}
     * @param dto {@link ${entity}DTO}
     */
    void create(${entity}DTO dto);

    /**
     * 更新${table.comment!table.name}
     * @param id 主键
     * @param dto {@link ${entity}DTO}
     */
    void update(Long id, ${entity}DTO dto);

    /**
     * 删除${table.comment!table.name}
     * @param id 主键
     */
    void delete(Long id);

    /**
     * 分页查询
     * @param pageDTO 分页条件
     * @return 分页结果VO
     */
    PageResult<${entity}VO> page(${entity}PageDTO pageDTO);

    /**
     * 详情
     * @param id 主键
     * @return VO
     */
    ${entity}VO detail(Long id);
}