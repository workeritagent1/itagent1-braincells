package org.worker.itagent1.admin.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName UserDTO
 * @description: 系统用户信息
 * @date 2023/4/22 23:06
 */
@Data
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;

}
