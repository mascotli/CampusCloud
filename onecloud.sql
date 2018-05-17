/*
Navicat MySQL Data Transfer

Source Server         : cloud-mysql
Source Server Version : 50721
Source Host           : mysql.mascot.com.cn:3306
Source Database       : onecloud

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-04-30 14:15:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ldt_create` datetime NOT NULL,
  `ldt_modified` datetime NOT NULL,
  `md5` char(32) NOT NULL,
  `size` bigint(20) unsigned NOT NULL,
  `type` varchar(100) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `md5` (`md5`),
  UNIQUE KEY `url` (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of file
-- ----------------------------

-- ----------------------------
-- Table structure for local_file
-- ----------------------------
DROP TABLE IF EXISTS `local_file`;
CREATE TABLE `local_file` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ldt_create` datetime NOT NULL,
  `ldt_modified` datetime NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `file_id` bigint(20) unsigned NOT NULL,
  `local_name` varchar(255) NOT NULL,
  `local_type` varchar(255) NOT NULL,
  `parent` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`parent`,`local_name`,`local_type`),
  UNIQUE KEY `UKfm1luhcs3efhhsfackqvjv89d` (`user_id`,`parent`,`local_name`,`local_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of local_file
-- ----------------------------

-- ----------------------------
-- Table structure for local_folder
-- ----------------------------
DROP TABLE IF EXISTS `local_folder`;
CREATE TABLE `local_folder` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ldt_create` datetime NOT NULL,
  `ldt_modified` datetime NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL,
  `local_name` varchar(255) NOT NULL,
  `parent` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`parent`,`local_name`),
  UNIQUE KEY `UKb90a2x9as0b1c1h4epgek33d6` (`user_id`,`parent`,`local_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of local_folder
-- ----------------------------
INSERT INTO `local_folder` VALUES ('1', '2017-10-10 10:10:10', '2017-10-10 10:10:10', '0', 'home', '0');
INSERT INTO `local_folder` VALUES ('2', '2017-10-10 10:10:10', '2017-10-10 10:10:10', '0', 'safebox', '0');
INSERT INTO `local_folder` VALUES ('3', '2017-10-10 10:10:10', '2017-10-10 10:10:10', '0', 'trash', '0');
INSERT INTO `local_folder` VALUES ('4', '2018-04-29 15:46:40', '2018-04-29 15:46:40', '1', 'document', '1');
INSERT INTO `local_folder` VALUES ('5', '2018-04-29 15:46:51', '2018-04-29 15:46:51', '1', 'software', '1');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `ldt_create` datetime NOT NULL,
  `ldt_modified` datetime NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nickname` varchar(30) NOT NULL,
  `photo_url` varchar(255) NOT NULL,
  `used_capacity` bigint(20) unsigned NOT NULL,
  `safe_password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `photo_url` (`photo_url`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '2017-10-10 10:10:10', '2017-10-10 10:10:10', 'admin', 'admin', 'admin', 'users/photo/001.jpg', '0', null);
