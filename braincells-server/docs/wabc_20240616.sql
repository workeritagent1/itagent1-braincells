/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.182.131
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : 192.168.182.131:3306
 Source Schema         : wabc

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 16/06/2024 16:27:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端唯一标识符',
  `resource_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端所能访问的Resource Server标识，逗号(,)分隔。',
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端应用程序的密码或密钥，用于安全认证。',
  `scope` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端申请的权限范围,包括read,write,trust;',
  `authorized_grant_types` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端支持的grant_type,包括authorization_code,password,refresh_token,implicit,client_credentials, 若支持多个grant_type用逗号(,)分隔',
  `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端的重定向URI,可为空, 当grant_type为authorization_code或implicit时, 在Oauth的流程中会使用并检查与注册时填写的redirect_uri是否一致.',
  `authorities` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指定客户端所拥有的Spring Security的权限值,可选,若有多个权限值,用逗号(,)分隔, 如: \"ROLE_UNITY,ROLE_USER\".',
  `access_token_validity` int(11) NULL DEFAULT NULL COMMENT '设定客户端的access_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时).',
  `refresh_token_validity` int(11) NULL DEFAULT NULL COMMENT '设定客户端的refresh_token的有效时间值(单位:秒),可选, 若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天).',
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '这是一个预留的字段,在Oauth的流程中没有实际的使用,可选,但若设置值,必须是JSON格式的数据,如:{\"country\":\"CN\",\"country_code\":\"086\"}',
  `autoapprove` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '设置用户是否自动Approval操作, 默认值为 \'false\', 可选值包括 \'true\',\'false\', \'read\',\'write\'.\r\n该字段只适用于grant_type=\"authorization_code\"的情况,当用户登录成功后,若该值为\'true\'或支持的scope值,则会跳过用户Approve的',
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('client01', NULL, '$2a$10$yZAgQ2DUIQAFJvZcmfBc5.dBplLAWcr.oYwaLnFVwj3xNiHng3PK.', 'all', 'authorization_code,implicit,password,client_credentials,refresh_token', 'https://baidu.com', NULL, 3600, 86400, NULL, 'false');

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部门名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '机构编码',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父节点id',
  `level` tinyint(4) NULL DEFAULT NULL COMMENT '机构层级',
  `tree_ids` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点id路径',
  `tree_codes` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点codes路径',
  `sort` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态(1:正常;0:禁用)',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(1:已删除;0:未删除)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` int(11) NULL DEFAULT NULL COMMENT '父菜单ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '路由路径(可以表示:service微服务名称，package代码包名称，directory文件夹目录，menu菜单名称)。\r\n为路径前后端路径：/service/package,directory/menu提供准备',
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径(vue页面完整路径，省略.vue后缀)',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目录和菜单图标',
  `sort` int(11) NULL DEFAULT NULL COMMENT '同级排序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '0停用，1启用',
  `directory` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '跳转路径',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'service微服务，package代码包名称，directory文件夹目录，menu菜单（package暂未用上）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1006 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (0, NULL, '统一网关', 'gateway', NULL, NULL, NULL, 1, NULL, 'service', '2024-01-08 23:34:22', NULL);
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', 'sysadmin', NULL, NULL, NULL, 1, NULL, 'service', '2024-01-08 23:35:51', NULL);
INSERT INTO `sys_menu` VALUES (1001, 1, '用户管理', 'sysUser', '', NULL, 1, 1, NULL, 'menu', '2024-01-08 23:38:01', NULL);
INSERT INTO `sys_menu` VALUES (1002, 1, '角色管理', 'sysRole', NULL, NULL, 2, 1, NULL, 'menu', '2024-01-08 23:38:38', NULL);
INSERT INTO `sys_menu` VALUES (1003, 1, '菜单管理', 'sysMenu', NULL, NULL, 3, 1, NULL, 'menu', '2024-01-08 23:39:14', NULL);
INSERT INTO `sys_menu` VALUES (1004, 1, '权限管理', 'sysPermission', NULL, NULL, 4, 1, NULL, 'menu', '2024-01-08 23:39:49', NULL);
INSERT INTO `sys_menu` VALUES (1005, 1, '机构管理', 'sysDept', NULL, NULL, 5, 1, NULL, 'menu', '2024-01-08 23:40:21', NULL);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `menu_id` int(11) NULL DEFAULT NULL COMMENT '权限所属菜单',
  `url_perm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'URL权限标识POST|GET|PUT|DELETE:/{service}/{menu}/{btn}',
  `btn_perm` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '按钮权限标识{directory}:{menu}:{btn}',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1, '查看用户', 1001, 'GET:/sysadmin/sysUser/*', 'system:sysUser:view', '2024-01-08 23:42:03', NULL);
INSERT INTO `sys_permission` VALUES (2, '编辑用户', 1001, 'PUT:/sysadmin/sysUser/*', 'system:sysUser:edit', '2024-01-08 23:44:54', NULL);
INSERT INTO `sys_permission` VALUES (3, '新增用户', 1001, 'POST:/sysadmin/sysUser/*', 'system:sysUser:add', '2024-01-08 23:44:57', NULL);
INSERT INTO `sys_permission` VALUES (4, '删除用户', 1001, 'DELETE:/sysadmin/sysUser/*', 'system:sysUser:delete', '2024-01-08 23:44:59', NULL);
INSERT INTO `sys_permission` VALUES (5, '查询角色权限', 1004, 'GET:/sysadmin/sysPermission/loadPermissionRoles', 'system:sysPermission:view', '2024-01-08 23:44:59', NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
  `sort` int(11) NULL DEFAULT NULL COMMENT '显示顺序',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '角色状态(1-正常；0-停用)',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0-未删除；1-已删除)',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '系统管理员', 'sysadmin', 1, 1, 0, '负责系统管理功能', '2024-01-08 23:09:53', '2024-01-08 23:09:55');
INSERT INTO `sys_role` VALUES (2, '网站管理员', 'webmaster', 2, 1, 0, '负责公司机构网站管理', '2024-01-08 23:12:36', '2024-01-08 23:12:39');
INSERT INTO `sys_role` VALUES (3, '博客管理员', 'blogadmin', 3, 1, 0, '负责博客管理', '2024-01-08 23:13:59', '2024-01-08 23:14:03');
INSERT INTO `sys_role` VALUES (4, '人工智能管理员', 'aigcadmin', 4, 1, 0, '负责人工智能中心的功能管理（chatgpt,cv,robot）', '2024-01-08 23:15:16', '2024-01-08 23:15:19');
INSERT INTO `sys_role` VALUES (5, '商务交易管理员', 'ExchangeManager', 5, 1, 0, '负责商业交易如买卖的公平公正、质量检查、交易推荐', '2024-01-08 23:19:34', '2024-01-08 23:19:37');
INSERT INTO `sys_role` VALUES (6, '多媒体内容管理员', 'MultimediaContentManager', 6, 1, 0, '负责文章、视频、图片、语音管理', '2024-01-08 23:24:48', '2024-01-08 23:24:51');
INSERT INTO `sys_role` VALUES (7, '客户端管理', 'clientadmin', 7, 1, 0, '负责管理客户端IOS,android,PC-exe,web浏览器,小程序、h5等端侧程序对接。', '2024-01-08 23:30:59', '2024-01-08 23:31:02');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 1001);

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission_id` int(11) NOT NULL COMMENT '资源权限id',
  PRIMARY KEY (`role_id`, `permission_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (2, 5);
INSERT INTO `sys_role_permission` VALUES (3, 5);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NULL DEFAULT 1 COMMENT '性别(0:未知1:男;2:女)',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `dept_id` int(11) NULL DEFAULT NULL COMMENT '部门ID',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `telephone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮箱',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '用户状态(1:正常;0:禁用)',
  `deleted` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除标识(0:未删除;1:已删除)',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', 'wabc', 1, '$2a$10$0Vb0IMD6KjNj4R71PwvzBu/QL5GjWMGZGrpF7zfJj9Y3RhvDoSqhG', 1, NULL, '13552594611', '2792818358@qq.com', 1, 0, '2023-12-31 22:44:52', '2023-12-31 22:44:57');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', 1);
INSERT INTO `sys_user_role` VALUES ('1', 2);

SET FOREIGN_KEY_CHECKS = 1;
