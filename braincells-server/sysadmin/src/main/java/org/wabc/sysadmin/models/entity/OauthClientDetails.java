package org.wabc.system.models.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *   OauthClientDetails没有继承BaseEntity，所以加上implements Serializable。
 * </p>
 *
 * @author wabc
 * @since 2024-01-06
 */
@Data
public class OauthClientDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户端唯一标识符
     */
    @TableId(type = IdType.INPUT)
    private String clientId;

    /**
     * 客户端所能访问的Resource Server标识，逗号(,)分隔。
     */
    private String resourceIds;

    /**
     * 客户端应用程序的密码或密钥，用于安全认证。
     */
    private String clientSecret;

    /**
     * 客户端申请的权限范围,包括read,write,trust;
     */
    private String scope;

    /**
     * 客户端支持的grant_type,包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔
     */
    private String authorizedGrantTypes;

    /**
     * 客户端的重定向URI,可为空, 当grant_type为authorization_code或implicit时, 在Oauth的流程中会使用并检查与注册时填写的redirect_uri是否一致.
     */
    private String webServerRedirectUri;

    /**
     * 指定客户端所拥有的Spring Security的权限值,可选,若有多个权限值,用逗号(,)分隔, 如: "ROLE_UNITY,ROLE_USER".
     */
    private String authorities;

    /**
     * 设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时).
     */
    private Integer accessTokenValidity;

    /**
     * 设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天).
     */
    private Integer refreshTokenValidity;

    /**
     * 这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,如:{"country":"CN","country_code":"086"}
     */
    private String additionalInformation;

    /**
     * 设置用户是否自动Approval操作, 默认值为 'false', 可选值包括 'true','false', 'read','write'.	该字段只适用于grant_type="authorization_code"的情况,当用户登录成功后,若该值为'true'或支持的scope值,则会跳过用户Approve的
     */
    private String autoapprove;

}
