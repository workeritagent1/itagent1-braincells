package ${mapperPackage};

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import ${entityPackage}.${entity};
import org.apache.ibatis.annotations.Mapper;

/**
 * ${table.comment!table.name} Mapper接口
 * <p>对数据库表${table.name}的CURD操作</p>
 * @author ${author}
 * @date ${date}
 */
@Mapper
public interface ${entity}Mapper extends BaseMapper<${entity}> {
}