package org.worker.itagent1.auth.oauth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("oauth_client_details")
@ApiModel(value = "OauthClientDetails对象", description = "")
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "client_id", type = IdType.AUTO)
    private String clientId;

    private String resourceIds;

    private String clientSecret;

    private String scope;

    private String authorizedGrantTypes;

    private String webServerRedirectUri;

    private String authorities;

    private Integer accessTokenValidity;

    private Integer refreshTokenValidity;

    private String additionalInformation;

    private String autoapprove;


}
