/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.18 : Database - OAManagingSystem
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

USE `OAManagingSystem`;

/*Table structure for table `ch_addresslist` */

DROP TABLE IF EXISTS `ch_addresslist`;

CREATE TABLE `ch_addresslist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addTime` datetime DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK573bhdxh4jtgi65heojv9c5rr` (`user_id`),
  CONSTRAINT `FK573bhdxh4jtgi65heojv9c5rr` FOREIGN KEY (`user_id`) REFERENCES `ch_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_asset` */

DROP TABLE IF EXISTS `ch_asset`;

CREATE TABLE `ch_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `addTime` datetime DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `invoices` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `repairPhone` varchar(255) DEFAULT NULL,
  `titleName` varchar(255) DEFAULT NULL,
  `assetType_id` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4937wox5cih7yfkiyrvyngrwx` (`assetType_id`),
  CONSTRAINT `FK4937wox5cih7yfkiyrvyngrwx` FOREIGN KEY (`assetType_id`) REFERENCES `ch_assettype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=182 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_assettype` */

DROP TABLE IF EXISTS `ch_assettype`;

CREATE TABLE `ch_assettype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `titleName` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_daily` */

DROP TABLE IF EXISTS `ch_daily`;

CREATE TABLE `ch_daily` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `hasSee` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `summary` longtext,
  PRIMARY KEY (`id`),
  KEY `FK4ey9xb1msfsjob8rf7t2ni03u` (`user_id`),
  KEY `FKel9gmmrs2q87fl49p4bg93b4y` (`parent_id`),
  CONSTRAINT `FK4ey9xb1msfsjob8rf7t2ni03u` FOREIGN KEY (`user_id`) REFERENCES `ch_user` (`id`),
  CONSTRAINT `FKel9gmmrs2q87fl49p4bg93b4y` FOREIGN KEY (`parent_id`) REFERENCES `ch_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_dept` */

DROP TABLE IF EXISTS `ch_dept`;

CREATE TABLE `ch_dept` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgrvpel4jja3qv9wlfopsixskr` (`dept_id`),
  CONSTRAINT `FKgrvpel4jja3qv9wlfopsixskr` FOREIGN KEY (`dept_id`) REFERENCES `ch_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_distributionrecord` */

DROP TABLE IF EXISTS `ch_distributionrecord`;

CREATE TABLE `ch_distributionrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lendTime` datetime DEFAULT NULL,
  `returnTime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL,
  `staff_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrfuybo1um7coaly8xa6eq2qhp` (`staff_id`),
  KEY `asset_id` (`asset_id`),
  CONSTRAINT `FKrfuybo1um7coaly8xa6eq2qhp` FOREIGN KEY (`staff_id`) REFERENCES `ch_staff` (`id`),
  CONSTRAINT `ch_distributionrecord_ibfk_1` FOREIGN KEY (`asset_id`) REFERENCES `ch_asset` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_draftbox` */

DROP TABLE IF EXISTS `ch_draftbox`;

CREATE TABLE `ch_draftbox` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acceptDetail` longtext,
  `content` longtext,
  `saveTime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `summary` longtext,
  `title` varchar(255) DEFAULT NULL,
  `sendPerson` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcwgrclgeoo0y8an1y0lxvrpjh` (`sendPerson`),
  CONSTRAINT `FKcwgrclgeoo0y8an1y0lxvrpjh` FOREIGN KEY (`sendPerson`) REFERENCES `ch_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_event` */

DROP TABLE IF EXISTS `ch_event`;

CREATE TABLE `ch_event` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `allDay` bit(1) DEFAULT NULL,
  `end` datetime DEFAULT NULL,
  `start` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_idIndex` (`user_id`),
  CONSTRAINT `FKan52aq43etgi8f0j3otkf7g5d` FOREIGN KEY (`user_id`) REFERENCES `ch_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_inbox` */

DROP TABLE IF EXISTS `ch_inbox`;

CREATE TABLE `ch_inbox` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `hasSee` int(11) DEFAULT NULL,
  `sendTime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `summary` longtext,
  `title` varchar(255) DEFAULT NULL,
  `accpetPerson` int(11) DEFAULT NULL,
  `sendPerson` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi2r7ipjh5yryl5gtv4gh525u9` (`accpetPerson`),
  KEY `FKhwehqjhjynamxqeekdgvu9y7s` (`sendPerson`),
  CONSTRAINT `FKhwehqjhjynamxqeekdgvu9y7s` FOREIGN KEY (`sendPerson`) REFERENCES `ch_user` (`id`),
  CONSTRAINT `FKi2r7ipjh5yryl5gtv4gh525u9` FOREIGN KEY (`accpetPerson`) REFERENCES `ch_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_menu` */

DROP TABLE IF EXISTS `ch_menu`;

CREATE TABLE `ch_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  `titleName` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `resource_id` int(11) DEFAULT NULL,
  `iconClass` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKn89wp54cv8rs7xt1x1fm021mh` (`resource_id`),
  CONSTRAINT `FKn89wp54cv8rs7xt1x1fm021mh` FOREIGN KEY (`resource_id`) REFERENCES `ch_resource` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_notification` */

DROP TABLE IF EXISTS `ch_notification`;

CREATE TABLE `ch_notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` longtext,
  `hasSee` int(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9na7iqb59g13uar20fr8mup9h` (`user_id`),
  CONSTRAINT `FK9na7iqb59g13uar20fr8mup9h` FOREIGN KEY (`user_id`) REFERENCES `ch_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_outbox` */

DROP TABLE IF EXISTS `ch_outbox`;

CREATE TABLE `ch_outbox` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `acceptDetail` longtext,
  `content` longtext,
  `sendTime` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `summary` longtext,
  `title` varchar(255) DEFAULT NULL,
  `sendPerson` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3ufuc81kbo3jvhe9v65e5litw` (`sendPerson`),
  CONSTRAINT `FK3ufuc81kbo3jvhe9v65e5litw` FOREIGN KEY (`sendPerson`) REFERENCES `ch_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_resource` */

DROP TABLE IF EXISTS `ch_resource`;

CREATE TABLE `ch_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  `titleName` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_role` */

DROP TABLE IF EXISTS `ch_role`;

CREATE TABLE `ch_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `titleName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_staff` */

DROP TABLE IF EXISTS `ch_staff`;

CREATE TABLE `ch_staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `birthday` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `salary` double DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `titleName` varchar(255) DEFAULT NULL,
  `dept_id` int(11) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `has_user` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK72qik6kug4hngyjvywb9b03g6` (`dept_id`),
  CONSTRAINT `FK72qik6kug4hngyjvywb9b03g6` FOREIGN KEY (`dept_id`) REFERENCES `ch_dept` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_user` */

DROP TABLE IF EXISTS `ch_user`;

CREATE TABLE `ch_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginTime` datetime DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  `username` varchar(255) DEFAULT NULL,
  `staff_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_brwssredo0l6g9udlld6yp8eq` (`staff_id`),
  CONSTRAINT `FKkvpgffhhnvcacj3nf10w2lidn` FOREIGN KEY (`staff_id`) REFERENCES `ch_staff` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Table structure for table `ch_visitrecord` */

DROP TABLE IF EXISTS `ch_visitrecord`;

CREATE TABLE `ch_visitrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `visitTime` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK20nmdi68d8obroq8dhpb70r0h` (`user_id`),
  CONSTRAINT `FK20nmdi68d8obroq8dhpb70r0h` FOREIGN KEY (`user_id`) REFERENCES `ch_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

/*Table structure for table `role_menu` */

DROP TABLE IF EXISTS `role_menu`;

CREATE TABLE `role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`),
  KEY `FKefbwjpoolopb5wlg73k9a2et2` (`menu_id`),
  CONSTRAINT `FK75qkkqnkyfs775cw322x2vgmh` FOREIGN KEY (`role_id`) REFERENCES `ch_role` (`id`),
  CONSTRAINT `FKefbwjpoolopb5wlg73k9a2et2` FOREIGN KEY (`menu_id`) REFERENCES `ch_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKoiybpobculjhpaex544hvrjh0` (`role_id`),
  CONSTRAINT `FKgdq496go1hbdmviyao9gaobcx` FOREIGN KEY (`user_id`) REFERENCES `ch_user` (`id`),
  CONSTRAINT `FKoiybpobculjhpaex544hvrjh0` FOREIGN KEY (`role_id`) REFERENCES `ch_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
