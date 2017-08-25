-- MySQL dump 10.13  Distrib 5.5.40, for debian-linux-gnu (i686)
--
-- Host: localhost    Database: vhoj
-- ------------------------------------------------------
-- Server version	5.5.40-0+wheezy1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- create db
set names utf8;
create database vhoj;
use vhoj;

--
-- Table structure for table `t_contest`
--

DROP TABLE IF EXISTS `t_contest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_contest` (
  `C_ID` int(10) NOT NULL AUTO_INCREMENT,
  `C_TITLE` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_DESCRIPTION` text COLLATE utf8_unicode_ci,
  `C_PASSWORD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_BEGINTIME` datetime DEFAULT NULL,
  `C_ENDTIME` datetime DEFAULT NULL,
  `C_MANAGER_ID` int(10) DEFAULT NULL,
  `C_HASH_CODE` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_REPLAY_STATUS_ID` int(10) unsigned DEFAULT NULL,
  `C_ANNOUNCEMENT` text COLLATE utf8_unicode_ci,
  `C_ENABLE_TIME_MACHINE` int(1) unsigned DEFAULT NULL,
  PRIMARY KEY (`C_ID`),
  KEY `Index_manager_id` (`C_MANAGER_ID`),
  KEY `Index_hash_code` (`C_HASH_CODE`),
  KEY `Index_replay_status_id` (`C_REPLAY_STATUS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_cproblem`
--

DROP TABLE IF EXISTS `t_cproblem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_cproblem` (
  `C_ID` int(10) NOT NULL AUTO_INCREMENT,
  `C_PROBLEM_ID` int(10) DEFAULT NULL,
  `C_CONTEST_ID` int(10) DEFAULT NULL,
  `C_NUM` varchar(2) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_TITLE` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_DESCRIPTION_ID` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`C_ID`),
  KEY `Index_problem_id` (`C_PROBLEM_ID`),
  KEY `Index_contest_id` (`C_CONTEST_ID`),
  KEY `Index_description_id` (`C_DESCRIPTION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_description`
--

DROP TABLE IF EXISTS `t_description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_description` (
  `C_ID` int(11) NOT NULL AUTO_INCREMENT,
  `C_DESCRIPTION` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `C_INPUT` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `C_OUTPUT` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `C_SAMPLEINPUT` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `C_SAMPLEOUTPUT` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `C_HINT` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `C_PROBLEM_ID` int(11) NOT NULL DEFAULT '0',
  `C_UPDATE_TIME` datetime DEFAULT NULL,
  `C_AUTHOR` varchar(100) DEFAULT NULL,
  `C_REMARKS` varchar(500) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_VOTE` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`C_ID`),
  KEY `Index_problem_id` (`C_PROBLEM_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_problem`
--

DROP TABLE IF EXISTS `t_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_problem` (
  `C_ID` int(10) NOT NULL AUTO_INCREMENT,
  `C_TITLE` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_SOURCE` varchar(20000) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_URL` varchar(500) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_originOJ` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_originProb` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_MEMORYLIMIT` int(10) DEFAULT NULL,
  `C_TIMELIMIT` int(10) unsigned DEFAULT NULL,
  `C_TRIGGER_TIME` datetime DEFAULT NULL,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_replay_status`
--

DROP TABLE IF EXISTS `t_replay_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_replay_status` (
  `C_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `C_DATA` mediumtext COLLATE utf8_unicode_ci,
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_submission`
--

DROP TABLE IF EXISTS `t_submission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_submission` (
  `C_ID` int(10) NOT NULL AUTO_INCREMENT,
  `C_STATUS` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_TIME` int(10) unsigned DEFAULT NULL,
  `C_MEMORY` int(10) unsigned DEFAULT NULL,
  `C_SUBTIME` datetime DEFAULT NULL,
  `C_PROBLEM_ID` int(10) DEFAULT NULL,
  `C_USER_ID` int(10) DEFAULT NULL,
  `C_CONTEST_ID` int(10) DEFAULT NULL,
  `C_LANGUAGE` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `C_SOURCE` text COLLATE utf8_unicode_ci,
  `C_ISOPEN` int(10) DEFAULT NULL,
  `C_DISP_LANGUAGE` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_USERNAME` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_ORIGIN_OJ` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_ORIGIN_PROB` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_IS_PRIVATE` int(10) unsigned DEFAULT '0',
  `C_ADDITIONAL_INFO` text COLLATE utf8_unicode_ci,
  `C_REAL_RUNID` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_REMOTE_ACCOUNT_ID` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_QUERY_COUNT` int(10) DEFAULT '0',
  `C_STATUS_UPDATE_TIME` datetime DEFAULT NULL,
  `C_REMOTE_SUBMIT_TIME` datetime DEFAULT NULL,
  `C_STATUS_CANONICAL` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_SOURCE_LENGTH` int(10) DEFAULT NULL,
  `C_LANGUAGE_CANONICAL` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_CONTEST_NUM` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`C_ID`),
  KEY `Index_problem_id` (`C_PROBLEM_ID`),
  KEY `Index_user_id` (`C_USER_ID`),
  KEY `Index_contest_id` (`C_CONTEST_ID`),
  KEY `Index_username` (`C_USERNAME`),
  KEY `Index_origin_prob` (`C_ORIGIN_PROB`),
  KEY `Index_status_canonical` (`C_STATUS_CANONICAL`),
  KEY `Index_origin_oj` (`C_ORIGIN_OJ`),
  KEY `Index_language_canonical` (`C_LANGUAGE_CANONICAL`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user`
--

DROP TABLE IF EXISTS `t_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user` (
  `C_ID` int(10) NOT NULL AUTO_INCREMENT,
  `C_USERNAME` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_NICKNAME` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_PASSWORD` varchar(40) COLLATE utf8_unicode_ci DEFAULT NULL,
  `C_CREATETIME` datetime DEFAULT NULL,
  `C_QQ` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `C_SCHOOL` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `C_EMAIL` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `C_BLOG` varchar(1000) COLLATE utf8_unicode_ci NOT NULL,
  `C_SHARE` int(10) unsigned NOT NULL DEFAULT '1',
  `C_SUP` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_user_session`
--

DROP TABLE IF EXISTS `t_user_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_user_session` (
  `C_ID` int(11) NOT NULL AUTO_INCREMENT,
  `C_ARRIVE_TIME` datetime DEFAULT NULL,
  `C_LOGIN_TIME` datetime DEFAULT NULL,
  `C_LEAVE_TIME` datetime DEFAULT NULL,
  `C_USER_AGENT` varchar(500) DEFAULT NULL,
  `C_REFERER` varchar(500) DEFAULT NULL,
  `C_IP` varchar(200) DEFAULT NULL,
  `C_USER_ID` int(11) DEFAULT NULL,
  `C_LOGIN_SUCCESS` int(11) DEFAULT NULL,
  PRIMARY KEY (`C_ID`),
  UNIQUE KEY `C_ID_UNIQUE` (`C_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-11-09 20:32:29
