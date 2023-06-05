package org.worker.itagent1.auth.oauth.dao;

import org.worker.itagent1.auth.oauth.entity.OauthCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itagent1.worker
 * @since 2023-06-04 09:49:52
 */
@Mapper
public interface OauthCodeMapper extends BaseMapper<OauthCode> {

}
