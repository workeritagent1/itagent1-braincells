package org.worker.itagent1.auth.oauth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Blob;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author itagent1.worker
 * @since 2023-06-04 09:49:52
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("oauth_refresh_token")
@ApiModel(value = "OauthRefreshToken对象", description = "")
public class OauthRefreshToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tokenId;

    private Blob token;

    private Blob authentication;


}
