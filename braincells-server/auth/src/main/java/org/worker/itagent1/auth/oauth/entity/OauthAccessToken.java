package org.worker.itagent1.auth.oauth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("oauth_access_token")
@ApiModel(value = "OauthAccessToken对象", description = "")
public class OauthAccessToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tokenId;

    private Blob token;

    @TableId(value = "authentication_id", type = IdType.AUTO)
    private String authenticationId;

    private String userName;

    private String clientId;

    private Blob authentication;

    private String refreshToken;


}
