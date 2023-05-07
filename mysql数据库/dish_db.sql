/*
Navicat MySQL Data Transfer

Source Server         : mysql5.6
Source Server Version : 50620
Source Host           : localhost:3306
Source Database       : dish_db

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2020-05-21 22:08:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('a', 'a');

-- ----------------------------
-- Table structure for `t_dish`
-- ----------------------------
DROP TABLE IF EXISTS `t_dish`;
CREATE TABLE `t_dish` (
  `dishId` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜谱id',
  `dishClassObj` int(11) NOT NULL COMMENT '菜谱类别',
  `dishName` varchar(50) NOT NULL COMMENT '菜谱名称',
  `dishPhoto` varchar(60) NOT NULL COMMENT '菜谱图片',
  `price` float NOT NULL COMMENT '参考价格',
  `dishDesc` varchar(800) NOT NULL COMMENT '菜谱介绍',
  `viewNum` int(11) NOT NULL COMMENT '浏览量',
  `addTime` varchar(20) DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`dishId`),
  KEY `dishClassObj` (`dishClassObj`),
  CONSTRAINT `t_dish_ibfk_1` FOREIGN KEY (`dishClassObj`) REFERENCES `t_dishclass` (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dish
-- ----------------------------
INSERT INTO `t_dish` VALUES ('1', '1', '青椒肉丝', 'upload/705583f1-29c1-413e-820d-e802d603704b.jpg', '18', '猪肉切丝，加入料酒、盐、味精、淀粉拌匀。\r\n青椒切成丝，加入盐拌匀，热油放入豆瓣、肉丝。\r\n加入料酒、酱油拌匀，加入青椒丝炒匀出锅。', '28', '2020-05-20 23:37:36');
INSERT INTO `t_dish` VALUES ('2', '1', '鱼香茄子', 'upload/d990a2d9-1ec7-435d-85bf-13601e4f154f.jpg', '12', '“起锅烧油放入切好的茄子炸至变软后捞出盛好，再往锅内倒入肉丁翻炒至变色，倒入蒜泥、小米椒、豆瓣酱炒匀。最后倒入炸好的茄子、调好的料汁翻炒均匀，一份美味的鱼香茄子就完成了。”', '10', '2020-05-21 20:17:28');
INSERT INTO `t_dish` VALUES ('3', '2', '胡萝卜炖鸡', 'upload/283cb9ef-4c93-482c-8cab-6c8c666e45d2.jpg', '45', '1.整只鸡切一半，（一般我们都买一整只鸡，保证是活的）。另一半冰冻起来。\r\n2.将鸡肉切小块。\r\n3.锅中放入少量的油，油热后放入鸡块翻炒。\r\n4.接着放入生姜，大蒜翻炒，去除腥味同时也提味。\r\n5.将胡萝卜外皮薄薄的一层刮去，以免影响口感。将胡萝卜切成滚刀片。\r\n6.放入适量的辣椒，待鸡肉变色后，炒出水分。\r\n7.接着放入胡萝卜继续翻炒。放入适量的盐。\r\n8.将炒好的鸡肉和胡萝卜倒入电压力锅中，放入适量的水，没过鸡肉为准', '31', '2020-05-21 21:51:56');

-- ----------------------------
-- Table structure for `t_dishclass`
-- ----------------------------
DROP TABLE IF EXISTS `t_dishclass`;
CREATE TABLE `t_dishclass` (
  `classId` int(11) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `className` varchar(40) NOT NULL COMMENT '类别名称',
  `classDesc` varchar(500) NOT NULL COMMENT '类别描述',
  PRIMARY KEY (`classId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dishclass
-- ----------------------------
INSERT INTO `t_dishclass` VALUES ('1', '川菜', '四川菜系');
INSERT INTO `t_dishclass` VALUES ('2', '湘菜', '湖南名菜系列');
INSERT INTO `t_dishclass` VALUES ('3', '东北菜', '东北人喜欢吃的菜');

-- ----------------------------
-- Table structure for `t_dishcollect`
-- ----------------------------
DROP TABLE IF EXISTS `t_dishcollect`;
CREATE TABLE `t_dishcollect` (
  `collectId` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏id',
  `dishObj` int(11) NOT NULL COMMENT '收藏菜谱',
  `userObj` varchar(30) NOT NULL COMMENT '收藏用户',
  `collectTime` varchar(20) DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`collectId`),
  KEY `dishObj` (`dishObj`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_dishcollect_ibfk_1` FOREIGN KEY (`dishObj`) REFERENCES `t_dish` (`dishId`),
  CONSTRAINT `t_dishcollect_ibfk_2` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dishcollect
-- ----------------------------
INSERT INTO `t_dishcollect` VALUES ('1', '1', '13688886666', '2020-05-20 23:37:50');
INSERT INTO `t_dishcollect` VALUES ('2', '1', '13688886666', '2020-05-21 13:01:25');

-- ----------------------------
-- Table structure for `t_leaveword`
-- ----------------------------
DROP TABLE IF EXISTS `t_leaveword`;
CREATE TABLE `t_leaveword` (
  `leaveWordId` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言id',
  `leaveTitle` varchar(80) NOT NULL COMMENT '留言标题',
  `leaveContent` varchar(2000) NOT NULL COMMENT '留言内容',
  `userObj` varchar(30) NOT NULL COMMENT '留言人',
  `leaveTime` varchar(20) DEFAULT NULL COMMENT '留言时间',
  `replyContent` varchar(1000) DEFAULT NULL COMMENT '管理回复',
  `replyTime` varchar(20) DEFAULT NULL COMMENT '回复时间',
  PRIMARY KEY (`leaveWordId`),
  KEY `userObj` (`userObj`),
  CONSTRAINT `t_leaveword_ibfk_1` FOREIGN KEY (`userObj`) REFERENCES `t_userinfo` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_leaveword
-- ----------------------------
INSERT INTO `t_leaveword` VALUES ('1', '我是四川人，多上点川菜吧', '我老公说我菜的味道不太好，麻烦多上几个川菜菜谱吧！', '13688886666', '2020-05-20 23:38:15', '好的', '2020-05-20 23:38:20');
INSERT INTO `t_leaveword` VALUES ('2', '湖南菜也不错', '我也喜欢吃湖南菜', '13688886666', '2020-05-21 17:28:32', '好的 ', '2020-05-21 21:53:03');

-- ----------------------------
-- Table structure for `t_notice`
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `noticeId` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告id',
  `title` varchar(80) NOT NULL COMMENT '标题',
  `content` varchar(800) NOT NULL COMMENT '公告内容',
  `publishDate` varchar(20) DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`noticeId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------
INSERT INTO `t_notice` VALUES ('1', '微信菜谱系统上线', '小伙伴们，快点来学做菜了！', '2020-05-13 23:38:28');
INSERT INTO `t_notice` VALUES ('2', '我们会陆续完善各种菜系', '慢慢来，以后各种菜都会上，麻烦你们把想吃的菜留言给管理员', '2020-05-21 21:54:37');

-- ----------------------------
-- Table structure for `t_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `t_userinfo`;
CREATE TABLE `t_userinfo` (
  `user_name` varchar(30) NOT NULL COMMENT 'user_name',
  `password` varchar(30) NOT NULL COMMENT '登录密码',
  `name` varchar(20) NOT NULL COMMENT '姓名',
  `gender` varchar(4) NOT NULL COMMENT '性别',
  `birthDate` varchar(20) DEFAULT NULL COMMENT '出生日期',
  `userPhoto` varchar(60) NOT NULL COMMENT '用户照片',
  `telephone` varchar(20) NOT NULL COMMENT '联系电话',
  `email` varchar(50) NOT NULL COMMENT '邮箱',
  `address` varchar(80) DEFAULT NULL COMMENT '家庭地址',
  `regTime` varchar(20) DEFAULT NULL COMMENT '注册时间',
  `openid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_userinfo
-- ----------------------------
INSERT INTO `t_userinfo` VALUES ('13688886666', '--', '鼠鼠', '男', '2020-01-01', 'upload/ae92c5f093b941578223a60771b88828', '--', '--', '--', '2020-05-21 13:00:30', 'oM7Mu5XyeVJSc8roaUCRlcz_IP9k');
INSERT INTO `t_userinfo` VALUES ('user1', '123', '张珊', '女', '2020-05-05', 'upload/b58d0101-b741-44a4-bfe7-bdd9cfd5c0a3.jpg', '13980083423', '2', '3', '2020-05-20 23:36:43', null);
