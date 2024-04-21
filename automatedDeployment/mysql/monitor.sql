-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: monitor
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `db_account`
--

DROP TABLE IF EXISTS `db_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_account` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `clients` json DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_email` (`email`),
  UNIQUE KEY `unique_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_account`
--

LOCK TABLES `db_account` WRITE;
/*!40000 ALTER TABLE `db_account` DISABLE KEYS */;
INSERT INTO `db_account` VALUES (1,'admin','1479539484@qq.com','$2a$10$FVT.RLuP6tI2q4poITHVQuNTLwz6wldhXXRsuidK0cOt.hQn.5MPe','admin',NULL,'2024-03-15 20:28:59');
/*!40000 ALTER TABLE `db_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_client`
--

DROP TABLE IF EXISTS `db_client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_client` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `node` varchar(255) DEFAULT NULL,
  `register_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_client`
--

LOCK TABLES `db_client` WRITE;
/*!40000 ALTER TABLE `db_client` DISABLE KEYS */;
/*!40000 ALTER TABLE `db_client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_client_detail`
--

DROP TABLE IF EXISTS `db_client_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_client_detail` (
  `id` int NOT NULL,
  `os_arch` varchar(255) DEFAULT NULL,
  `os_name` varchar(255) DEFAULT NULL,
  `os_version` varchar(255) DEFAULT NULL,
  `os_bit` int DEFAULT NULL,
  `cpu_name` varchar(255) DEFAULT NULL,
  `cpu_core` int DEFAULT NULL,
  `memory` double DEFAULT NULL,
  `disk` double DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_client_detail`
--

LOCK TABLES `db_client_detail` WRITE;
/*!40000 ALTER TABLE `db_client_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `db_client_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_client_ssh`
--

DROP TABLE IF EXISTS `db_client_ssh`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_client_ssh` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ssh连接id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `client_id` int DEFAULT NULL COMMENT '客户端id',
  `port` int DEFAULT NULL COMMENT '连接端口',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '连接用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '连接密码',
  `ip` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_client_ssh`
--

LOCK TABLES `db_client_ssh` WRITE;
/*!40000 ALTER TABLE `db_client_ssh` DISABLE KEYS */;
/*!40000 ALTER TABLE `db_client_ssh` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_client_warn`
--

DROP TABLE IF EXISTS `db_client_warn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_client_warn` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '警告id',
  `client_id` int DEFAULT NULL COMMENT '客户端id',
  `detail` json DEFAULT NULL COMMENT '详细信息',
  `time` datetime DEFAULT NULL COMMENT '警告时间',
  `description` varchar(256) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_client_warn`
--

LOCK TABLES `db_client_warn` WRITE;
/*!40000 ALTER TABLE `db_client_warn` DISABLE KEYS */;
/*!40000 ALTER TABLE `db_client_warn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_client_warn_rules`
--

DROP TABLE IF EXISTS `db_client_warn_rules`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_client_warn_rules` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '规则id',
  `client_id` int DEFAULT NULL COMMENT '客户端id',
  `name` varchar(127) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '规则名称',
  `cpu_warn` double DEFAULT '80' COMMENT 'cpu告警阈值',
  `memory_warn` double DEFAULT '80' COMMENT '内存告警阈值',
  `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '规则描述',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_client_warn_rules`
--

LOCK TABLES `db_client_warn_rules` WRITE;
/*!40000 ALTER TABLE `db_client_warn_rules` DISABLE KEYS */;
/*!40000 ALTER TABLE `db_client_warn_rules` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_develop_subtask`
--

DROP TABLE IF EXISTS `db_develop_subtask`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_develop_subtask` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '子任务id',
  `name` varchar(127) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '任务名称',
  `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '任务描述',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '任务结束时间',
  `status` tinyint DEFAULT '0' COMMENT '状态(0未开始,1进行中,2已完成)',
  `task_id` int DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `update_user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_develop_subtask`
--

LOCK TABLES `db_develop_subtask` WRITE;
/*!40000 ALTER TABLE `db_develop_subtask` DISABLE KEYS */;
/*!40000 ALTER TABLE `db_develop_subtask` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `db_develop_task`
--

DROP TABLE IF EXISTS `db_develop_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `db_develop_task` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `name` varchar(127) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '任务名称',
  `principal_ids` json DEFAULT NULL COMMENT '项目小组ids',
  `type` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '任务类型(例:测试)',
  `description` text CHARACTER SET utf8mb3 COLLATE utf8mb3_unicode_ci COMMENT '任务描述',
  `about_client_id` varchar(256) COLLATE utf8mb3_unicode_ci DEFAULT NULL COMMENT '任务相关服务器id',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '任务结束时间',
  `status` tinyint(1) DEFAULT '0' COMMENT '状态(0未开始,1进行中,2已完成)',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `db_develop_task`
--

LOCK TABLES `db_develop_task` WRITE;
/*!40000 ALTER TABLE `db_develop_task` DISABLE KEYS */;
/*!40000 ALTER TABLE `db_develop_task` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-21  0:14:26
