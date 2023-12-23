package org.wabc.auth.admin;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Builder;
import org.wabc.auth.base.BaseEntity;

import java.util.List;

/**
 * @author itagent1.worker
 * @version 1.0
 * @ClassName User
 * @description:
 * @date 2023/12/21 22:29
 */
//@Data
@Builder
public class SysUser extends BaseEntity {
    @TableId(type = IdType.AUTO)
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
    @TableLogic(value = "0", delval = "1")
    private Integer deleted;

    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private List<String> roleIds;

    @TableField(exist = false)
    private String roleNames;

    @TableField(exist = false)
    private List<String> roles;

}