package org.wabc.auth.admin;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName SysUserDTO
 * @description:
 * @date 2023/12/21 23:07
 */
@Builder
@Data
public class SysUserDTO {
    private String id;
    private String username;
    private String password;
    private String nickname;
    private Integer gender;
    private String avatar;
    private String mobile;
    private String email;
    private Integer status;
    private String deptId;

    // "逻辑删除标识(1:删除;0:正常)"
    private Integer deleted;

    private String deptName;

    private List<String> roleIds;

    private String roleNames;

    private List<String> roles;
}
