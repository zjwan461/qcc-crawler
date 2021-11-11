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
  `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT '序号',
  `root_comp` varchar(255) NOT NULL,
  `comp_name` varchar(255) NOT NULL COMMENT '公司名称',
  `legal_person` varchar(255) DEFAULT NULL COMMENT '法人',
  `capital` varchar(255) DEFAULT NULL COMMENT '注册资本',
  `level` int unsigned NOT NULL COMMENT '层级',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
