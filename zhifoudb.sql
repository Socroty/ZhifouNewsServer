-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: zhifoudb
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comment_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_info` (
  `comment_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `comment_user_id` int(10) unsigned NOT NULL,
  `comment_data_id` int(10) unsigned NOT NULL,
  `comment_content` text NOT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `comment_info_fk_1` (`comment_user_id`),
  KEY `comment_info_fk_2` (`comment_data_id`),
  CONSTRAINT `comment_info_fk_1` FOREIGN KEY (`comment_user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_info_fk_2` FOREIGN KEY (`comment_data_id`) REFERENCES `data_info` (`data_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_info`
--

LOCK TABLES `comment_info` WRITE;
/*!40000 ALTER TABLE `comment_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `comment_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `data_info` (
  `data_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `data_title` varchar(100) NOT NULL,
  `data_author` varchar(40) NOT NULL,
  `data_content` text NOT NULL,
  `data_url` varchar(200) NOT NULL,
  `data_category` varchar(40) NOT NULL,
  `data_hash_id` varchar(40) NOT NULL,
  `data_image_url` varchar(200) NOT NULL,
  `data_create_time` datetime NOT NULL,
  `data_profiles` text,
  PRIMARY KEY (`data_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_info`
--

LOCK TABLES `data_info` WRITE;
/*!40000 ALTER TABLE `data_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite_info` (
  `favorite_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `favorite_user_id` int(10) unsigned NOT NULL,
  `favorite_data_id` int(10) unsigned NOT NULL,
  `favorite_data_category` char(40) NOT NULL,
  `favorite_data_title` char(200) NOT NULL,
  `favorite_data_image_url` char(200) NOT NULL,
  PRIMARY KEY (`favorite_id`),
  KEY `favorite_info_fk_1` (`favorite_user_id`),
  KEY `favorite_info_fk_2` (`favorite_data_id`),
  CONSTRAINT `favorite_info_fk_1` FOREIGN KEY (`favorite_user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `favorite_info_fk_2` FOREIGN KEY (`favorite_data_id`) REFERENCES `data_info` (`data_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite_info`
--

LOCK TABLES `favorite_info` WRITE;
/*!40000 ALTER TABLE `favorite_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorite_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `follow_info` (
  `follow_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `follow_user_id` int(10) unsigned NOT NULL,
  `follow_author_name` varchar(40) NOT NULL,
  PRIMARY KEY (`follow_id`),
  KEY `follow_info_fk` (`follow_user_id`),
  CONSTRAINT `follow_info_fk` FOREIGN KEY (`follow_user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow_info`
--

LOCK TABLES `follow_info` WRITE;
/*!40000 ALTER TABLE `follow_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `follow_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `headline_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `headline_info` (
  `headline_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `headline_title` char(200) NOT NULL,
  `headline_content` text NOT NULL,
  `headline_background` varchar(100) NOT NULL,
  PRIMARY KEY (`headline_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `headline_info`
--

LOCK TABLES `headline_info` WRITE;
/*!40000 ALTER TABLE `headline_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `headline_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `history_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `history_info` (
  `history_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `history_user_id` int(10) unsigned NOT NULL,
  `history_data_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`history_id`),
  KEY `history_info_fk_1` (`history_user_id`),
  KEY `history_info_fk_2` (`history_data_id`),
  CONSTRAINT `history_info_fk_1` FOREIGN KEY (`history_user_id`) REFERENCES `user_info` (`user_id`) ON UPDATE CASCADE,
  CONSTRAINT `history_info_fk_2` FOREIGN KEY (`history_data_id`) REFERENCES `data_info` (`data_id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history_info`
--

LOCK TABLES `history_info` WRITE;
/*!40000 ALTER TABLE `history_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `history_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile_info` (
  `profile_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `profile_data_category_first` varchar(40) DEFAULT NULL,
  `profile_data_category_second` varchar(40) DEFAULT NULL,
  `profile_data_category_third` varchar(40) DEFAULT NULL,
  `profile_data_author_first` varchar(40) DEFAULT NULL,
  `profile_data_author_second` varchar(40) DEFAULT NULL,
  `profile_data_author_third` varchar(40) DEFAULT NULL,
  `profile_user_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`profile_id`),
  KEY `profile_info_fk` (`profile_user_id`),
  CONSTRAINT `profile_info_fk` FOREIGN KEY (`profile_user_id`) REFERENCES `user_info` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile_info`
--

LOCK TABLES `profile_info` WRITE;
/*!40000 ALTER TABLE `profile_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户代码',
  `user_name` char(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `user_description` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户简介',
  `user_sign_up_date` datetime NOT NULL COMMENT '注册时间',
  `user_password` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `user_random` int(11) NOT NULL COMMENT '校验代码',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'zhifoudb'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-10 12:53:39
