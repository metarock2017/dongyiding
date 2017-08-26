/*
Navicat MySQL Data Transfer

Source Server         : 你是小白兔吗
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : java

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2017-08-26 15:42:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `people_max` int(2) DEFAULT NULL,
  `undercover` int(1) DEFAULT NULL,
  `word_id` int(11) NOT NULL DEFAULT '0',
  `room_id` int(4) NOT NULL,
  `judge` varchar(255) NOT NULL,
  `undercover1` int(2) DEFAULT NULL,
  `undercover2` int(2) DEFAULT NULL,
  `undercover3` int(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;
