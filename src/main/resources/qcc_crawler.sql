/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : qcc_crawler

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 12/11/2021 00:08:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for export_data
-- ----------------------------
DROP TABLE IF EXISTS `export_data`;
CREATE TABLE `export_data` (
  `id` int unsigned auto_increment not null comment 'id',
  `name` varchar(255) NOT NULL comment '公司名称',
  `href` varchar(500) NOT NULL COMMENT '企查查链接',
  `parent` varchar(255) DEFAULT NULL COMMENT '父公司名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
