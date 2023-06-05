package org.worker.itagent1.auth.oauth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("oauth_approvals")
@ApiModel(value = "OauthApprovals对象", description = "")
public class OauthApprovals implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String clientId;

    private String scope;

    private String status;

    private LocalDateTime expiresAt;

    private LocalDateTime lastModifiedAt;


}
