//package org.wabc.sysadmin.models.entity;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableLogic;
//import com.baomidou.mybatisplus.annotation.TableName;
//import lombok.Data;
//import org.wabc.commons.base.BaseEntity;
//
///**
// * <p>
// *
// * </p>
// *
// * @author wabc
// * @since 2024-01-06
// */
//@Data
//@TableName("sys_dept")
//public class SysDept extends BaseEntity {
//    /**
//     * 主键
//     */
//    @TableId(type= IdType.AUTO)
//    private Long id;
//
//    /**
//     * 部门名称
//     */
//    private String name;
//
//    /**
//     * 机构编码
//     */
//    private String code;
//
//    /**
//     * 父节点id
//     */
//    private Integer parentId;
//
//    /**
//     * 机构等级
//     */
//    private Integer level;
//
//    /**
//     * 父节点id路径
//     */
//    private String treeIds;
//
//    /**
//     * 父节点编码路径
//     */
//    private String treeCodes;
//
//
//     /**
//     * 显示顺序
//     */
//    private Integer sort;
//
//    /**
//     * 用户状态(1:正常;0:禁用)
//     */
//    private Integer status;
//
//    /**
//     * 逻辑删除标识(0:未删除;1:已删除)
//     */
//    @TableLogic(value = "0", delval = "1")
//    private Integer deleted;
//
//}
//
//
