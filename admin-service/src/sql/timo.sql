/*
 Navicat Premium Data Transfer

 Source Server         : 本地localhost-mysql
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : localhost:3306
 Source Schema         : timo

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 20/04/2023 20:08:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- ----------------------------
-- Table structure for sys_admin_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_admin_log`;
CREATE TABLE `sys_admin_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '编号',
  `description` varchar(100) NOT NULL DEFAULT '' COMMENT '操作描述',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '操作用户',
  `create_date` datetime NOT NULL COMMENT '操作时间',
  `spend_time` int NOT NULL COMMENT '消耗时间',
  `base_path` varchar(500) NOT NULL DEFAULT '' COMMENT '根路径',
  `uri` varchar(500) NOT NULL DEFAULT '' COMMENT 'URI',
  `url` varchar(500) NOT NULL DEFAULT '' COMMENT 'URL',
  `method` varchar(10) NOT NULL COMMENT '请求方法',
  `parameter` mediumtext  NOT NULL COMMENT '请求参数',
  `user_agent` varchar(500) NOT NULL COMMENT '用户标识',
  `ip` varchar(30) NOT NULL COMMENT 'IP地址',
  `status_code` varchar(12) NOT NULL COMMENT '状态码',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of sys_admin_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT NULL COMMENT '部门名称',
  `pid` bigint DEFAULT NULL COMMENT '父级ID',
  `pids` varchar(255) DEFAULT NULL COMMENT '所有父级编号',
  `sort` int DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint DEFAULT NULL COMMENT '更新用户',
  `status` tinyint DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
INSERT INTO `sys_dept` VALUES (1, '总公司', 0, '[0]', 1, '', '2018-12-02 17:41:23', '2019-02-23 02:41:28', 1, 1, 1);
INSERT INTO `sys_dept` VALUES (2, '技术部门', 1, '[0],[1]', 1, '', '2018-12-02 17:51:04', '2019-04-27 13:12:46', 1, 1, 1);
INSERT INTO `sys_dept` VALUES (3, '市场部门', 1, '[0],[1]', 2, '', '2018-12-02 17:51:42', '2019-04-27 13:12:20', 1, 1, 1);
INSERT INTO `sys_dept` VALUES (4, '研发部门', 1, '[0],[1]', 3, '', '2018-12-02 17:51:55', '2019-04-27 13:12:20', 1, 1, 1);
INSERT INTO `sys_dept` VALUES (5, '测试部门', 1, '[0],[1]', 4, '', '2018-12-02 17:52:07', '2019-04-27 13:12:20', 1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT NULL COMMENT '字典名称',
  `name` varchar(255) DEFAULT NULL COMMENT '字典键名',
  `type` tinyint DEFAULT NULL COMMENT '字典类型',
  `value` text COMMENT '字典键值',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint DEFAULT NULL COMMENT '更新用户',
  `status` tinyint DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (1, '数据状态', 'DATA_STATUS', 2, '1:正常,2:冻结,3:删除', '', '2018-10-05 16:03:11', '2018-10-05 16:11:41', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (2, '字典类型', 'DICT_TYPE', 2, '2:键值对', '', '2018-10-05 20:08:55', '2019-01-17 23:39:23', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (3, '用户性别', 'USER_SEX', 2, '1:男,2:女', '', '2018-10-05 20:12:32', '2018-10-05 20:12:32', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (4, '菜单类型', 'MENU_TYPE', 2, '1:目录,2:菜单,3:按钮', '', '2018-10-05 20:24:57', '2019-11-06 20:08:46', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (5, '搜索栏状态', 'SEARCH_STATUS', 2, '1:正常,2:冻结', '', '2018-10-05 20:25:45', '2019-02-26 00:34:34', 1, 1, 1);
INSERT INTO `sys_dict` VALUES (6, '日志类型', 'LOG_TYPE', 2, '1:业务,2:登录,3:系统', '', '2018-10-05 20:28:47', '2019-02-26 00:31:43', 1, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `path` varchar(255) DEFAULT NULL COMMENT '文件路径',
  `mime` varchar(255) DEFAULT NULL COMMENT 'MIME文件类型',
  `size` bigint DEFAULT NULL COMMENT '文件大小',
  `md5` varchar(255) DEFAULT NULL COMMENT 'MD5值',
  `sha1` varchar(255) DEFAULT NULL COMMENT 'SHA1值',
  `create_by` bigint DEFAULT NULL COMMENT '上传者',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of sys_file
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `pid` bigint DEFAULT NULL COMMENT '父级编号',
  `pids` varchar(255) DEFAULT NULL COMMENT '所有父级编号',
  `url` varchar(255) DEFAULT NULL COMMENT 'URL地址',
  `perms` varchar(255) DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(255) DEFAULT NULL COMMENT '图标',
  `type` tinyint DEFAULT NULL COMMENT '类型（1:一级菜单,2:子级菜单,3:不是菜单）',
  `sort` int DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint DEFAULT NULL COMMENT '更新用户',
  `status` tinyint DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES (1, '菜单管理', 2, '[0],[2]', '/system/menu/index', 'system:menu:index', '', 2, 3, '', '2018-09-29 00:02:10', '2019-02-24 16:10:40', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (2, '系统管理', 0, '[0]', '#', '#', 'fa fa-cog', 1, 2, '', '2018-09-29 00:05:50', '2019-02-27 21:34:56', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (3, '添加', 1, '[0],[2],[1]', '/system/menu/add', 'system:menu:add', '', 3, 1, '', '2018-09-29 00:06:57', '2019-02-24 16:12:59', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (4, '角色管理', 2, '[0],[2]', '/system/role/index', 'system:role:index', '', 2, 2, '', '2018-09-29 00:08:14', '2019-02-24 16:10:34', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (5, '添加', 4, '[0],[2],[4]', '/system/role/add', 'system:role:add', '', 3, 1, '', '2018-09-29 00:09:04', '2023-04-09 23:25:33', 1, 1, 3);
INSERT INTO `sys_menu` VALUES (6, '主页', 0, '[0]', '/index', 'index', 'layui-icon layui-icon-home', 1, 1, '', '2018-09-29 00:09:56', '2019-02-27 21:34:56', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (7, '用户管理', 2, '[0],[2]', '/system/user/index', 'system:user:index', '', 2, 1, '', '2018-09-29 00:43:50', '2019-04-05 17:43:25', 1, 2, 1);
INSERT INTO `sys_menu` VALUES (8, '编辑', 1, '[0],[2],[1]', '/system/menu/edit', 'system:menu:edit', '', 3, 2, '', '2018-09-29 00:57:33', '2019-02-24 16:13:05', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (9, '详细', 1, '[0],[2],[1]', '/system/menu/detail', 'system:menu:detail', '', 3, 3, '', '2018-09-29 01:03:00', '2019-02-24 16:13:12', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (10, '修改状态', 1, '[0],[2],[1]', '/system/menu/status', 'system:menu:status', '', 3, 4, '', '2018-09-29 01:03:43', '2019-02-24 16:13:21', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (11, '编辑', 4, '[0],[2],[4]', '/system/role/edit', 'system:role:edit', '', 3, 2, '', '2018-09-29 01:06:13', '2019-02-24 16:12:10', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (12, '授权', 4, '[0],[2],[4]', '/system/role/auth', 'system:role:auth', '', 3, 3, '', '2018-09-29 01:06:57', '2019-02-24 16:12:17', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (13, '详细', 4, '[0],[2],[4]', '/system/role/detail', 'system:role:detail', '', 3, 4, '', '2018-09-29 01:08:00', '2019-02-24 16:12:24', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (14, '修改状态', 4, '[0],[2],[4]', '/system/role/status', 'system:role:status', '', 3, 5, '', '2018-09-29 01:08:22', '2019-02-24 16:12:43', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (15, '编辑', 7, '[0],[2],[7]', '/system/user/edit', 'system:user:edit', '', 3, 2, '', '2018-09-29 21:17:17', '2019-02-24 16:11:03', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (16, '添加', 7, '[0],[2],[7]', '/system/user/add', 'system:user:add', '', 3, 1, '', '2018-09-29 21:17:58', '2019-02-24 16:10:28', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (17, '修改密码', 7, '[0],[2],[7]', '/system/user/pwd', 'system:user:pwd', '', 3, 3, '', '2018-09-29 21:19:40', '2019-02-24 16:11:11', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (18, '权限分配', 7, '[0],[2],[7]', '/system/user/role', 'system:user:role', '', 3, 4, '', '2018-09-29 21:20:35', '2019-02-24 16:11:18', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (19, '详细', 7, '[0],[2],[7]', '/system/user/detail', 'system:user:detail', '', 3, 5, '', '2018-09-29 21:21:00', '2019-02-24 16:11:26', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (20, '修改状态', 7, '[0],[2],[7]', '/system/user/status', 'system:user:status', '', 3, 6, '', '2018-09-29 21:21:18', '2019-02-24 16:11:35', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (21, '字典管理', 2, '[0],[2]', '/system/dict/index', 'system:dict:index', '', 2, 5, '', '2018-10-05 00:55:52', '2019-02-24 16:14:24', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (22, '字典添加', 21, '[0],[2],[21]', '/system/dict/add', 'system:dict:add', '', 3, 1, '', '2018-10-05 00:56:26', '2019-02-24 16:14:10', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (23, '字典编辑', 21, '[0],[2],[21]', '/system/dict/edit', 'system:dict:edit', '', 3, 2, '', '2018-10-05 00:57:08', '2019-02-24 16:14:34', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (24, '字典详细', 21, '[0],[2],[21]', '/system/dict/detail', 'system:dict:detail', '', 3, 3, '', '2018-10-05 00:57:42', '2019-02-24 16:14:41', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (25, '修改状态', 21, '[0],[2],[21]', '/system/dict/status', 'system:dict:status', '', 3, 4, '', '2018-10-05 00:58:27', '2019-02-24 16:14:49', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (26, '行为日志', 2, '[0],[2]', '/system/actionLog/index', 'system:actionLog:index', '', 2, 6, '', '2018-10-14 16:52:01', '2019-02-27 21:34:15', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (27, '日志详细', 26, '[0],[2],[26]', '/system/actionLog/detail', 'system:actionLog:detail', '', 3, 1, '', '2018-10-14 21:07:11', '2019-02-27 21:34:15', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (28, '日志删除', 26, '[0],[2],[26]', '/system/actionLog/delete', 'system:actionLog:delete', '', 3, 2, '', '2018-10-14 21:08:17', '2019-02-27 21:34:15', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (30, '开发中心', 0, '[0]', '#', '#', 'fa fa-gavel', 1, 3, '', '2018-10-19 16:38:23', '2023-04-13 00:44:47', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (31, '代码生成', 30, '[0],[30]', '/dev/code', '#', '', 2, 5, '', '2018-10-19 16:39:04', '2023-04-13 00:55:00', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (125, '表单构建', 30, '[0],[30]', '/dev/build', '#', '', 2, 7, '', '2018-11-25 16:12:23', '2023-04-13 00:45:16', 1, 1, 2);
INSERT INTO `sys_menu` VALUES (136, '部门管理', 2, '[0],[2]', '/system/dept/index', 'system:dept:index', '', 2, 4, '', '2018-12-02 16:33:23', '2019-02-24 16:10:50', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (137, '添加', 136, '[0],[2],[136]', '/system/dept/add', 'system:dept:add', '', 3, 1, '', '2018-12-02 16:33:23', '2019-02-24 16:13:34', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (138, '编辑', 136, '[0],[2],[136]', '/system/dept/edit', 'system:dept:edit', '', 3, 2, '', '2018-12-02 16:33:23', '2019-02-24 16:13:42', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (139, '详细', 136, '[0],[2],[136]', '/system/dept/detail', 'system:dept:detail', '', 3, 3, '', '2018-12-02 16:33:23', '2019-02-24 16:13:49', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (140, '改变状态', 136, '[0],[2],[136]', '/system/dept/status', 'system:dept:status', '', 3, 4, '', '2018-12-02 16:33:23', '2019-02-24 16:13:57', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (146, '数据接口', 30, '[0],[30]', '/dev/swagger', '#', '22222', 2, 8, '', '2018-12-09 21:11:11', '2023-04-12 23:45:32', 1, 1, 0);
INSERT INTO `sys_menu` VALUES (157, '编辑菜单测试', 30, '[0],[30]', '2222', '222', '22222', 2, 6, '排序2', '2023-04-11 01:20:03', '2023-04-13 00:45:26', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (165, 'test88888', 0, '[0]', '/dev/code', 'dev:code', 'fa fa-cog', 1, 4, '', '2023-04-13 02:03:33', '2023-04-14 23:04:33', 1, 1, 3);
INSERT INTO `sys_menu` VALUES (168, '3333', 165, '[0],[165]', '3333', '333', '22222', 2, 1, '', '2023-04-13 03:25:15', '2023-04-17 09:55:00', 1, 1, 2);
INSERT INTO `sys_menu` VALUES (169, 'xx', 165, '[0],[165]', 'xxx', 'xx', '', 2, 2, '', '2023-04-16 22:51:19', '2023-04-16 22:51:19', 1, 1, 1);
INSERT INTO `sys_menu` VALUES (170, 'xxxxx', 165, '[0],[165]', '/dev/code', 'dev:code', '22222', 2, 3, 'aaaaa', '2023-04-17 09:54:32', '2023-04-17 09:54:42', 1, 1, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(255) DEFAULT NULL COMMENT '角色名称（中文名）',
  `name` varchar(255) DEFAULT NULL COMMENT '标识名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint DEFAULT NULL COMMENT '创建用户',
  `update_by` bigint DEFAULT NULL COMMENT '更新用户',
  `status` tinyint DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', '', '2018-09-29 00:12:40', '2023-04-20 19:51:24', 5, 1, 1);
INSERT INTO `sys_role` VALUES (2, '开发组', 'group', '', '2018-09-30 16:04:32', '2019-04-28 00:10:00', 1, 1, 1);
INSERT INTO `sys_role` VALUES (3, '用户组', 'group1', '', '2018-09-30 16:24:20', '2023-04-15 20:37:38', 1, 1, 1);
INSERT INTO `sys_role` VALUES (5, 'xxxx2', 'test2', 'xxx2', '2023-04-20 18:50:23', '2023-04-20 19:39:26', 5, 1, 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL,
  `menu_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (3, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (2, 2);
INSERT INTO `sys_role_menu` VALUES (3, 2);
INSERT INTO `sys_role_menu` VALUES (5, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (3, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (3, 4);
INSERT INTO `sys_role_menu` VALUES (5, 4);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (3, 6);
INSERT INTO `sys_role_menu` VALUES (5, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (2, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (3, 8);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (3, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (3, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (2, 11);
INSERT INTO `sys_role_menu` VALUES (3, 11);
INSERT INTO `sys_role_menu` VALUES (5, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (2, 12);
INSERT INTO `sys_role_menu` VALUES (3, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (2, 13);
INSERT INTO `sys_role_menu` VALUES (3, 13);
INSERT INTO `sys_role_menu` VALUES (5, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (2, 14);
INSERT INTO `sys_role_menu` VALUES (3, 14);
INSERT INTO `sys_role_menu` VALUES (5, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (2, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (2, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (2, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (2, 19);
INSERT INTO `sys_role_menu` VALUES (1, 20);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (3, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (3, 22);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (3, 23);
INSERT INTO `sys_role_menu` VALUES (1, 24);
INSERT INTO `sys_role_menu` VALUES (3, 24);
INSERT INTO `sys_role_menu` VALUES (1, 25);
INSERT INTO `sys_role_menu` VALUES (3, 25);
INSERT INTO `sys_role_menu` VALUES (1, 26);
INSERT INTO `sys_role_menu` VALUES (1, 27);
INSERT INTO `sys_role_menu` VALUES (1, 28);
INSERT INTO `sys_role_menu` VALUES (1, 30);
INSERT INTO `sys_role_menu` VALUES (3, 30);
INSERT INTO `sys_role_menu` VALUES (5, 30);
INSERT INTO `sys_role_menu` VALUES (1, 31);
INSERT INTO `sys_role_menu` VALUES (3, 31);
INSERT INTO `sys_role_menu` VALUES (5, 31);
INSERT INTO `sys_role_menu` VALUES (1, 125);
INSERT INTO `sys_role_menu` VALUES (1, 136);
INSERT INTO `sys_role_menu` VALUES (3, 136);
INSERT INTO `sys_role_menu` VALUES (1, 137);
INSERT INTO `sys_role_menu` VALUES (3, 137);
INSERT INTO `sys_role_menu` VALUES (1, 138);
INSERT INTO `sys_role_menu` VALUES (3, 138);
INSERT INTO `sys_role_menu` VALUES (1, 139);
INSERT INTO `sys_role_menu` VALUES (3, 139);
INSERT INTO `sys_role_menu` VALUES (1, 140);
INSERT INTO `sys_role_menu` VALUES (3, 140);
INSERT INTO `sys_role_menu` VALUES (1, 146);
INSERT INTO `sys_role_menu` VALUES (3, 157);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `nickname` varchar(255) DEFAULT NULL COMMENT '用户昵称',
  `password` char(64) DEFAULT NULL COMMENT '密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '密码盐',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `picture` varchar(255) DEFAULT NULL COMMENT '头像',
  `sex` tinyint DEFAULT NULL COMMENT '性别（1:男,2:女）',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(255) DEFAULT NULL COMMENT '电话号码',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint DEFAULT NULL COMMENT '状态（1:正常,2:冻结,3:删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, 'admin', '超级管理员', '5fa04c0758ae596e2a93cd2802640693a5b08bdfacd4307abce323c85e481154', '7rl2t9', 1, NULL, 1, '10086@163.com', '10086', '', '2023-03-09 23:00:03', '2019-11-06 20:09:17', 1);
INSERT INTO `sys_user` VALUES (2, 'cavlin', '普通用户', 'bdafa055b91b7d3868b3590402aa2116da96ea043516a550b6a75b0e31141188', '4yW9Pw', 2, NULL, 2, '1008612@qq.com', '1008612', '', '2018-09-30 16:25:22', '2023-03-28 11:14:36', 1);
INSERT INTO `sys_user` VALUES (3, 'costins', 'costins2', '6c6e9ab4f98e6c6365e82e555a84d123e26d753060a660921ad77644ffc57550', 'Eb63LO', NULL, NULL, 1, 'xx@sina.com', '15888888881', 'xxxxxee', '2023-04-14 04:48:34', '2023-04-20 09:55:05', 1);
INSERT INTO `sys_user` VALUES (4, 'aaaaaa', 'aaaaaa', 'b4d016aa3f540ceb1ed096616bfc7069ae83970615969c953000cf0056eefa12', 'oa8q75', NULL, NULL, 1, 'xx@sina.com', '15888888888', '', '2023-04-14 04:52:43', '2023-04-14 04:52:43', 3);
INSERT INTO `sys_user` VALUES (5, 'zhada', '渣打用户', 'edd4e9d80d0483c2663c3c08562b7ce61c03ea821af39119a9e6a438d3074d4f', '607Xb4', NULL, NULL, 1, 'xx@sina.com', '15888888888', 'xxx', '2023-04-17 09:41:53', '2023-04-20 19:10:37', 1);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
INSERT INTO `sys_user_role` VALUES (2, 3);
INSERT INTO `sys_user_role` VALUES (3, 3);
INSERT INTO `sys_user_role` VALUES (5, 5);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
