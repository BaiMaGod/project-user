/*
 Navicat Premium Data Transfer

 Source Server         : localMysql
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : base_user

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 12/11/2019 10:37:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `userId` int(11) NOT NULL AUTO_INCREMENT COMMENT 'userId，自增\r\n',
  `roleId` int(11) NULL DEFAULT 1 COMMENT '角色id',
  `number` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `nickName` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `headImg` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `telPhone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'QQ号',
  `weiXin` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '微信号',
  `sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `readName` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `birthday` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '生日',
  `introduce` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '简介',
  `createTime` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updateTime` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 3, 'rootroot', 'a906449d5769fa7361d7ecc6aa3f6d28', NULL, NULL, '1245678912', '123456@qq.com', '879846156', 'w65645616', '男', '张三', '2019-05-01', '说法萨法', '2019-11-06 22:51:53', '2019-11-07 09:49:11');
INSERT INTO `user` VALUES (2, 2, 'user001', 'a906449d5769fa7361d7ecc6aa3f6d28', NULL, NULL, '1245678912', '123456@qq.com', '879846156', 'w65645616', '男', '张三', '2019-05-01', '说法萨法', NULL, '2019-11-07 22:06:15');
INSERT INTO `user` VALUES (4, 2, 'zhangsan', '9cbf8a4dcb8e30682b927f352d6559a0', '比司法', NULL, '211414141', '123456@qq.com', '441424234', 'w234243242', '男', '张三', '1988-01-04', '示范法', '2019-11-07 13:12:23', '2019-11-07 22:03:11');
INSERT INTO `user` VALUES (6, 1, '749865308@qq.com', '9cbf8a4dcb8e30682b927f352d6559a0', NULL, NULL, NULL, '749865308@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, '2019-11-08 09:42:16', NULL);

SET FOREIGN_KEY_CHECKS = 1;
