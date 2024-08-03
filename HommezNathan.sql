CREATE DATABASE  IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `mydb`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `componenten`
--

DROP TABLE IF EXISTS `componenten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `componenten` (
                               `idcomponenten` int NOT NULL AUTO_INCREMENT,
                               `producent` varchar(45) DEFAULT NULL,
                               `eenheidsprijs` float DEFAULT NULL,
                               `voorraad_aantal` int NOT NULL,
                               PRIMARY KEY (`idcomponenten`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `componenten`
--

LOCK TABLES `componenten` WRITE;
/*!40000 ALTER TABLE `componenten` DISABLE KEYS */;
INSERT INTO `componenten` VALUES (1,'Futurlec',0.25,0),(2,'Futurlec',0.2,6),(3,'Futurlec',0.25,28),(4,'Futurlec',0.2,4),(5,'Futurlec',0.3,32),(6,'Futurlec',0.5,38),(7,'Futurlec',0.55,17),(8,'Futurlec',0.35,12),(9,'Futurlec',0.25,24),(10,'Futurlec',0.25,9),(11,'Futurlec',0.42,31),(12,'Futurlec',0.32,29),(13,'Futurlec',0.5,17),(14,'Futurlec',0.3,24),(15,'Futurlec',0.45,42),(16,'Futurlec',0.4,15),(17,'Futurlec',0.4,26),(18,'Futurlec',0.65,33),(19,'Futurlec',0.85,42),(20,'Futurlec',0.4,23),(21,'Futurlec',1.3,39),(22,'Futurlec',0.65,41),(23,'Futurlec',1.45,11),(24,'Futurlec',1.4,9),(25,'Arduino',22.8,2),(26,'Raspberry Pi',87.95,2),(27,'Arduino',42,3),(28,'Arduino',21.6,5),(29,'Arduino',25,5),(30,'SAMIORE ROBOT',4.11,5),(31,'TENSTAR ROBOT',2.5,5),(32,'Raspberry Pi',69.95,8),(33,'Raspberry Pi',29.95,8),(34,'Whadda',14.9,10),(35,'Arduino',24,15),(36,'Arduino',25.8,15),(37,NULL,2.47,0),(38,'YAGEO',1.4,49),(39,'NEOHM',1.08,31),(40,'YAGEO',1.43,31),(41,'YAGEO',2.09,7),(42,'YAGEO',0.69,35),(43,'NEOHM',1.98,21),(44,'YAGEO',1.89,11),(45,'VISHAY',1.58,49),(46,'YAGEO',0.84,9),(47,'VISHAY',0.91,9),(48,'VISHAY',1.62,4),(49,'VISHAY',1.59,19),(50,'YAGEO',1.28,15),(51,'VISHAY',2.28,10),(52,'VISHAY',1.96,41),(53,'VISHAY',1.46,33),(54,'YAGEO',1.96,20),(55,'NEOHM',1.17,28),(56,'VISHAY',1.7,31),(57,'NEOHM',1.5,2),(58,'VISHAY',1.51,5),(59,'VISHAY',0.96,26),(60,'NEOHM',2.39,11),(61,'NEOHM',1.98,14),(62,'NEOHM',2.3,44),(63,'NEOHM',1.57,29),(64,'YAGEO',1.07,41),(65,'NEOHM',1.22,1),(66,'NEOHM',0.71,34),(67,'Futurlec',0.25,32),(68,'Futurlec',0.2,36),(69,'Futurlec',0.25,43),(70,'Futurlec',0.2,4),(71,'Futurlec',0.3,32),(72,'Futurlec',0.5,38),(73,'Futurlec',0.55,17),(74,'Futurlec',0.35,42),(75,'Futurlec',0.25,24),(76,'Futurlec',0.25,9),(77,'Futurlec',0.42,31),(78,'Futurlec',0.32,29),(79,'Futurlec',0.5,17),(80,'Futurlec',0.3,24),(81,'Futurlec',0.45,42),(82,'Futurlec',0.4,15),(83,'Futurlec',0.4,26),(84,'Futurlec',0.65,33),(85,'Futurlec',0.85,42),(86,'Futurlec',0.4,23),(87,'Futurlec',1.3,39),(88,'Futurlec',0.65,41),(89,'Futurlec',1.45,11),(90,'Futurlec',1.4,9),(91,'Arduino',22.8,2),(92,'Raspberry Pi',87.95,2),(93,'Arduino',42,3),(94,'Arduino',21.6,5),(95,'Arduino',25,5),(96,'SAMIORE ROBOT',4.11,5),(97,'TENSTAR ROBOT',2.5,5),(98,'Raspberry Pi',69.95,8),(99,'Raspberry Pi',29.95,8),(100,'Whadda',14.9,10),(101,'Arduino',24,15),(102,'Arduino',25.8,15);
/*!40000 ALTER TABLE `componenten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `docenten`
--

DROP TABLE IF EXISTS `docenten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `docenten` (
                            `docenten_id` int NOT NULL,
                            `vakgebied` enum('ELO','ICT') NOT NULL,
                            `beginjaar` date DEFAULT NULL,
                            PRIMARY KEY (`docenten_id`),
                            KEY `fk_docenten_personen1_idx` (`docenten_id`),
                            CONSTRAINT `fk_docenten_personen1` FOREIGN KEY (`docenten_id`) REFERENCES `personen` (`idpersonen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `docenten`
--

LOCK TABLES `docenten` WRITE;
/*!40000 ALTER TABLE `docenten` DISABLE KEYS */;
INSERT INTO `docenten` VALUES (3,'ELO','2018-06-26'),(4,'ELO','2017-06-26'),(6,'ICT','2005-06-21'),(8,'ICT','2000-12-10');
/*!40000 ALTER TABLE `docenten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ics`
--

DROP TABLE IF EXISTS `ics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ics` (
                       `componenten_idcomponenten` int NOT NULL,
                       `voedingsspanning` float NOT NULL,
                       `aantalpinnen` tinyint NOT NULL,
                       `serieaanduiding` varchar(20) NOT NULL,
                       `typeaanduiding` varchar(100) NOT NULL,
                       PRIMARY KEY (`componenten_idcomponenten`),
                       CONSTRAINT `fk_ic's_componenten1` FOREIGN KEY (`componenten_idcomponenten`) REFERENCES `componenten` (`idcomponenten`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ics`
--

LOCK TABLES `ics` WRITE;
/*!40000 ALTER TABLE `ics` DISABLE KEYS */;
INSERT INTO `ics` VALUES (1,5,14,'74HC','\"74HC00 Quad 2-input NAND Gate\"'),(2,5,14,'74HC','\"74HC02 Quad 2-input NOR Gate\"'),(3,5,14,'74HC','\"74HC04 Hex Inverter\"'),(4,5,14,'74HC','\"74HC08 Quad 2-input AND Gate\"'),(5,5,14,'74HC','\"74HC74 Dual D Flip-Flop\"'),(6,5,16,'74HC','\"74HC75 Quad BiStable Transparent Latch\"'),(7,5,16,'74HC','\"74HC85 4-bit Magnitude Comparator\"'),(8,5,16,'74HC','\"74HC139 2 to 4 Decoder/Demultiplexer\"'),(9,5,16,'74HC','\"74HC148 8 to 3 Line Priority Decoder\"'),(10,5,16,'74HC','\"74HC157 Quad 2-input Data Multiplexers\"'),(11,5,16,'74HC','\"74HC160 Decade Counter with Asynchronous Clear\"'),(12,5,16,'74HC','\"74HC161 Synchronous 4-bit Counter\"'),(13,5,16,'74HC','\"74HC238 3 to 8 Line Decoder/Demultiplexer\"'),(14,5,16,'74HC','\"74HC257 Quad 2-Input Data Selector/Multiplexer\"'),(15,5,20,'74HC','\"74HC240 Octal Inverter Line Driver Tri-State\"'),(16,5,20,'74HC','\"74HC244 Octal Driver Tri-State\"'),(17,5,20,'74HC','\"74HC245 Octal Bus Transceiver Tri-State\"'),(18,5,20,'74HC','\"74HC273 Octal D-type Flip-Flop with Clear\"'),(19,5,20,'74HC','\"74HC299 8-bit Universal Shift Register\"'),(20,5,20,'74HC','\"74HC373 Octal D Flip-Flop Tri-State\"'),(21,5,20,'74HC','\"74HC645 Octal Bus Transceiver Tri-State\"'),(22,5,20,'74HC','\"74HC688 8-bit Magnitude Comparator\"'),(23,5,24,'74HC','\"74HC154 4 to 16 Line Decoder/Demultiplexer\"'),(24,5,24,'74HC','\"74HC4067 16-Channel Analog Multiplexer\"'),(67,5,14,'74HC','\"74HC00 Quad 2-input NAND Gate\"'),(68,5,14,'74HC','\"74HC02 Quad 2-input NOR Gate\"'),(69,5,14,'74HC','\"74HC04 Hex Inverter\"'),(70,5,14,'74HC','\"74HC08 Quad 2-input AND Gate\"'),(71,5,14,'74HC','\"74HC74 Dual D Flip-Flop\"'),(72,5,16,'74HC','\"74HC75 Quad BiStable Transparent Latch\"'),(73,5,16,'74HC','\"74HC85 4-bit Magnitude Comparator\"'),(74,5,16,'74HC','\"74HC139 2 to 4 Decoder/Demultiplexer\"'),(75,5,16,'74HC','\"74HC148 8 to 3 Line Priority Decoder\"'),(76,5,16,'74HC','\"74HC157 Quad 2-input Data Multiplexers\"'),(77,5,16,'74HC','\"74HC160 Decade Counter with Asynchronous Clear\"'),(78,5,16,'74HC','\"74HC161 Synchronous 4-bit Counter\"'),(79,5,16,'74HC','\"74HC238 3 to 8 Line Decoder/Demultiplexer\"'),(80,5,16,'74HC','\"74HC257 Quad 2-Input Data Selector/Multiplexer\"'),(81,5,20,'74HC','\"74HC240 Octal Inverter Line Driver Tri-State\"'),(82,5,20,'74HC','\"74HC244 Octal Driver Tri-State\"'),(83,5,20,'74HC','\"74HC245 Octal Bus Transceiver Tri-State\"'),(84,5,20,'74HC','\"74HC273 Octal D-type Flip-Flop with Clear\"'),(85,5,20,'74HC','\"74HC299 8-bit Universal Shift Register\"'),(86,5,20,'74HC','\"74HC373 Octal D Flip-Flop Tri-State\"'),(87,5,20,'74HC','\"74HC645 Octal Bus Transceiver Tri-State\"'),(88,5,20,'74HC','\"74HC688 8-bit Magnitude Comparator\"'),(89,5,24,'74HC','\"74HC154 4 to 16 Line Decoder/Demultiplexer\"'),(90,5,24,'74HC','\"74HC4067 16-Channel Analog Multiplexer\"');
/*!40000 ALTER TABLE `ics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notities`
--

DROP TABLE IF EXISTS `notities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notities` (
                            `idnotities` int NOT NULL AUTO_INCREMENT,
                            `notities` text NOT NULL,
                            `tijdstip` datetime NOT NULL,
                            `uitlenen_iduitlenen` int NOT NULL,
                            PRIMARY KEY (`idnotities`),
                            KEY `fk_notities_uitlenen1_idx` (`uitlenen_iduitlenen`),
                            CONSTRAINT `fk_notities_uitlenen1` FOREIGN KEY (`uitlenen_iduitlenen`) REFERENCES `uitlenen` (`iduitlenen`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notities`
--

LOCK TABLES `notities` WRITE;
/*!40000 ALTER TABLE `notities` DISABLE KEYS */;
INSERT INTO `notities` VALUES (1,'kmldjgfqkmldsjfn','2024-06-07 17:01:17',3),(2,'Hella','2024-06-08 20:44:54',5),(3,'Dit moet zeker op tijd ingeleverd worden','2024-06-11 10:05:41',7);
/*!40000 ALTER TABLE `notities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `personen`
--

DROP TABLE IF EXISTS `personen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `personen` (
                            `idpersonen` int NOT NULL AUTO_INCREMENT,
                            `voornaam` varchar(45) NOT NULL,
                            `familienaam` varchar(45) NOT NULL,
                            `nummer` char(10) DEFAULT NULL,
                            PRIMARY KEY (`idpersonen`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `personen`
--

LOCK TABLES `personen` WRITE;
/*!40000 ALTER TABLE `personen` DISABLE KEYS */;
INSERT INTO `personen` VALUES (1,'Nathan','Hommez','9458692987'),(2,'Basille','Van Hootegem','6733891143'),(3,'Bart','Degeest','1178607382'),(4,'Mark','Brutsaert','3288754472'),(5,'Nathan','Hommez','2516708546'),(6,'bobo','dodo','9545909179'),(7,'jinx','zaun','1726558743'),(8,'guus','loccufier','8983058748'),(9,'bo','jenk','1660117840');
/*!40000 ALTER TABLE `personen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialecomponenten`
--

DROP TABLE IF EXISTS `specialecomponenten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specialecomponenten` (
                                       `componenten_idcomponenten` int NOT NULL,
                                       `naam` varchar(45) NOT NULL,
                                       `beschrijving` text,
                                       PRIMARY KEY (`componenten_idcomponenten`),
                                       CONSTRAINT `fk_specialecomponenten_componenten1` FOREIGN KEY (`componenten_idcomponenten`) REFERENCES `componenten` (`idcomponenten`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialecomponenten`
--

LOCK TABLES `specialecomponenten` WRITE;
/*!40000 ALTER TABLE `specialecomponenten` DISABLE KEYS */;
INSERT INTO `specialecomponenten` VALUES (25,'Arduino Nano 33 BLE','Arduino\'s tiniest form factor with Bluetooth and Bluetooth  Low Energy�and embedded inertial sensor'),(26,'Raspberry Pi 5 - 8GB','Raspberry Pi 5 bouwt voort op het fenomenale succes van de Raspberry Pi 4. In vergelijking met zijn voorganger levert hij 2-3x hogere CPU-prestaties en een aanzienlijke verbetering van de GPU-prestaties. naast verbeteringen aan camera. beeldscherm en USB-interfacing.'),(27,'Arduino Mega 2560 Rev3','The 8-bit board with 54 digital pins. 16 analog inputs. and 4 serial ports.'),(28,'Arduino Nano','The classic Arduino Nano is the smallest board to build your projects with.'),(29,'Arduino UNO R4 WiFi','The Arduino UNO R4 WiFi merges the RA4M1 microprocessor from Renesas with the ESP32-S3 from Espressif. creating an all-in-one tool for makers with enhanced processing power and a diverse array of new peripherals. With its built-in Wi-Fi  and Bluetooth  capabilities. the UNO R4 WiFi enables makers to venture into boundless creative possibilities. Furthermore. this versatile board boasts a convenient on-board 12x8 LED matrix and a Qwiic connector. offering ample space for innovation and unleashing creativity. This dynamic combination empowers makers to transform their ideas into reality and elevate their projects to unprecedented heights.'),(30,'Arduino UNO R3','The �Arduino UNO �is the best board to get started with electronics and coding. If this is your first experience tinkering with the platform. the UNO is the most robust board you can start playing with. The UNO is the most used and documented board of the whole Arduino family.'),(31,'Nodemcu V3','Draadloze Module Ch340 Cp2102 Nodemcu V3 V2 Lua Wifi Internet Of Things Development Board Gebaseerd Esp8266 ESP-12E'),(32,'Raspberry Pi 4 Model B 4GB + behuizing','De Raspberry Pi 4 Model B 4GB is de populairste van de familie. en de uitvoering die je wil kopen als je de Raspberry Pi als desktop-computer wil gaan gebruiken. Door de 4GB RAM merkt je dat hij bij desktopgebruik wat sneller reageert dan de 2GB uitvoering. Zeker bij multi-tasking. of wat meer geheugen intensieve applicaties zal je de 2GB extra RAM kunnen waarderen.'),(33,'Raspberry Pi Camera 3','De standaard Raspberry Pi Camera Module 3 is een compacte camera van Raspberry Pi met een beeldhoek van 66 graden. Hij beschikt over een IMX708 12-megapixelsensor met HDR. en heeft fasedetectie autofocus.'),(34,'NODEMCU V2 LUA ESP8266','\"De NodeMcu is een open source-firmware en een development kit. die u helpt bij het prototypen van uw Internet of Things\"\" (IoT) producten met enkele Lua-scriptlijnen.\"\"\"'),(35,'Arduino UNO R3','The �Arduino UNO �is the best board to get started with electronics and coding. If this is your first experience tinkering with the platform. the UNO is the most robust board you can start playing with. The UNO is the most used and documented board of the whole Arduino family.'),(36,'Arduino Ethernet Shield Rev2','The Arduino Ethernet Shield 2 connects your Arduino to the internet.'),(91,'Arduino Nano 33 BLE','Arduino\'s tiniest form factor with Bluetooth and Bluetooth  Low Energy�and embedded inertial sensor'),(92,'Raspberry Pi 5 - 8GB','Raspberry Pi 5 bouwt voort op het fenomenale succes van de Raspberry Pi 4. In vergelijking met zijn voorganger levert hij 2-3x hogere CPU-prestaties en een aanzienlijke verbetering van de GPU-prestaties. naast verbeteringen aan camera. beeldscherm en USB-interfacing.'),(93,'Arduino Mega 2560 Rev3','The 8-bit board with 54 digital pins. 16 analog inputs. and 4 serial ports.'),(94,'Arduino Nano','The classic Arduino Nano is the smallest board to build your projects with.'),(95,'Arduino UNO R4 WiFi','The Arduino UNO R4 WiFi merges the RA4M1 microprocessor from Renesas with the ESP32-S3 from Espressif. creating an all-in-one tool for makers with enhanced processing power and a diverse array of new peripherals. With its built-in Wi-Fi  and Bluetooth  capabilities. the UNO R4 WiFi enables makers to venture into boundless creative possibilities. Furthermore. this versatile board boasts a convenient on-board 12x8 LED matrix and a Qwiic connector. offering ample space for innovation and unleashing creativity. This dynamic combination empowers makers to transform their ideas into reality and elevate their projects to unprecedented heights.'),(96,'Arduino UNO R3','The �Arduino UNO �is the best board to get started with electronics and coding. If this is your first experience tinkering with the platform. the UNO is the most robust board you can start playing with. The UNO is the most used and documented board of the whole Arduino family.'),(97,'Nodemcu V3','Draadloze Module Ch340 Cp2102 Nodemcu V3 V2 Lua Wifi Internet Of Things Development Board Gebaseerd Esp8266 ESP-12E'),(98,'Raspberry Pi 4 Model B 4GB + behuizing','De Raspberry Pi 4 Model B 4GB is de populairste van de familie. en de uitvoering die je wil kopen als je de Raspberry Pi als desktop-computer wil gaan gebruiken. Door de 4GB RAM merkt je dat hij bij desktopgebruik wat sneller reageert dan de 2GB uitvoering. Zeker bij multi-tasking. of wat meer geheugen intensieve applicaties zal je de 2GB extra RAM kunnen waarderen.'),(99,'Raspberry Pi Camera 3','De standaard Raspberry Pi Camera Module 3 is een compacte camera van Raspberry Pi met een beeldhoek van 66 graden. Hij beschikt over een IMX708 12-megapixelsensor met HDR. en heeft fasedetectie autofocus.'),(100,'NODEMCU V2 LUA ESP8266','\"De NodeMcu is een open source-firmware en een development kit. die u helpt bij het prototypen van uw Internet of Things\"\" (IoT) producten met enkele Lua-scriptlijnen.\"\"\"'),(101,'Arduino UNO R3','The �Arduino UNO �is the best board to get started with electronics and coding. If this is your first experience tinkering with the platform. the UNO is the most robust board you can start playing with. The UNO is the most used and documented board of the whole Arduino family.'),(102,'Arduino Ethernet Shield Rev2','The Arduino Ethernet Shield 2 connects your Arduino to the internet.');
/*!40000 ALTER TABLE `specialecomponenten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studenten`
--

DROP TABLE IF EXISTS `studenten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studenten` (
                             `studenten_id` int NOT NULL,
                             PRIMARY KEY (`studenten_id`),
                             CONSTRAINT `fk_studenten_personen1` FOREIGN KEY (`studenten_id`) REFERENCES `personen` (`idpersonen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studenten`
--

LOCK TABLES `studenten` WRITE;
/*!40000 ALTER TABLE `studenten` DISABLE KEYS */;
INSERT INTO `studenten` VALUES (1),(2),(5),(7),(9);
/*!40000 ALTER TABLE `studenten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uitlenen`
--

DROP TABLE IF EXISTS `uitlenen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uitlenen` (
                            `iduitlenen` int NOT NULL AUTO_INCREMENT,
                            `datum` datetime NOT NULL,
                            `tijdstip` date NOT NULL,
                            `ontleendaan_id` int NOT NULL,
                            `ontleenddoor_id` int NOT NULL,
                            PRIMARY KEY (`iduitlenen`),
                            KEY `fk_uitlenen_personen1_idx` (`ontleendaan_id`),
                            KEY `fk_uitlenen_docenten1_idx` (`ontleenddoor_id`),
                            CONSTRAINT `fk_uitlenen_docenten1` FOREIGN KEY (`ontleenddoor_id`) REFERENCES `docenten` (`docenten_id`),
                            CONSTRAINT `fk_uitlenen_personen1` FOREIGN KEY (`ontleendaan_id`) REFERENCES `personen` (`idpersonen`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uitlenen`
--

LOCK TABLES `uitlenen` WRITE;
/*!40000 ALTER TABLE `uitlenen` DISABLE KEYS */;
INSERT INTO `uitlenen` VALUES (1,'2024-06-07 10:27:31','2024-06-21',2,3),(2,'2024-06-07 10:44:35','2024-06-21',2,3),(3,'2024-06-07 10:47:31','2024-06-21',1,4),(4,'2024-06-07 13:07:08','2024-06-21',2,3),(5,'2024-06-08 20:44:34','2024-06-08',1,3),(6,'2024-06-11 09:32:16','2024-06-11',5,6),(7,'2024-06-11 10:05:30','2024-06-12',1,3),(8,'2024-06-12 15:13:17','2024-06-12',1,8);
/*!40000 ALTER TABLE `uitlenen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uitlenen_has_componenten`
--

DROP TABLE IF EXISTS `uitlenen_has_componenten`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `uitlenen_has_componenten` (
                                            `uitlenen_iduitlenen` int NOT NULL,
                                            `componenten_idcomponenten` int NOT NULL,
                                            `aantalcomponenten` smallint NOT NULL,
                                            `aantal_terugbrengingen` int DEFAULT NULL,
                                            `terugbrengdatum` datetime DEFAULT NULL,
                                            PRIMARY KEY (`uitlenen_iduitlenen`,`componenten_idcomponenten`),
                                            KEY `fk_uitlenen_has_componenten_componenten1_idx` (`componenten_idcomponenten`),
                                            KEY `fk_uitlenen_has_componenten_uitlenen1_idx` (`uitlenen_iduitlenen`),
                                            CONSTRAINT `fk_uitlenen_has_componenten_componenten1` FOREIGN KEY (`componenten_idcomponenten`) REFERENCES `componenten` (`idcomponenten`),
                                            CONSTRAINT `fk_uitlenen_has_componenten_uitlenen1` FOREIGN KEY (`uitlenen_iduitlenen`) REFERENCES `uitlenen` (`iduitlenen`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uitlenen_has_componenten`
--

LOCK TABLES `uitlenen_has_componenten` WRITE;
/*!40000 ALTER TABLE `uitlenen_has_componenten` DISABLE KEYS */;
INSERT INTO `uitlenen_has_componenten` VALUES (1,1,25,25,'2024-06-07 17:50:30'),(1,37,5,NULL,NULL),(2,1,5,NULL,NULL),(3,37,5,5,'2024-06-07 17:49:04'),(4,1,20,20,'2024-06-08 20:47:03'),(5,2,30,30,'2024-06-08 20:45:20'),(6,8,30,30,'2024-06-12 12:16:59'),(7,3,15,5,'2024-06-11 10:06:28'),(8,46,40,35,'2024-06-12 15:14:58');
/*!40000 ALTER TABLE `uitlenen_has_componenten` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `weerstanden`
--

DROP TABLE IF EXISTS `weerstanden`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `weerstanden` (
                               `componenten_idcomponenten` int NOT NULL,
                               `weerstandwaarde` float NOT NULL,
                               `tolerantie` float NOT NULL,
                               `banden` tinyint NOT NULL,
                               `maximaalvermogen` float DEFAULT NULL,
                               PRIMARY KEY (`componenten_idcomponenten`),
                               CONSTRAINT `fk_weerstanden_componenten` FOREIGN KEY (`componenten_idcomponenten`) REFERENCES `componenten` (`idcomponenten`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `weerstanden`
--

LOCK TABLES `weerstanden` WRITE;
/*!40000 ALTER TABLE `weerstanden` DISABLE KEYS */;
INSERT INTO `weerstanden` VALUES (37,1,0.05,5,0.5),(38,2.2,1,4,0.25),(39,2.2,0.05,5,0.25),(40,2.2,0.01,5,0.25),(41,4.7,0.01,4,1),(42,10,0.25,5,0.25),(43,33,0.5,4,1),(44,68,0.1,4,0.25),(45,100,0.1,5,1),(46,3300,2,5,1),(47,6800,0.05,5,1),(48,15000,0.1,4,0.25),(49,22000,2,5,0.25),(50,22000,1,5,0.5),(51,68000,0.01,5,0.25),(52,100000,0.05,4,0.25),(53,150000,0.02,5,1),(54,220000,0.5,4,1),(55,330000,0.5,5,1),(56,680000,2,4,0.25),(57,1500000,0.5,5,0.25),(58,3300000,0.25,4,0.5),(59,4700000,0.05,4,0.5),(60,33000000,0.1,5,1),(61,470000000,0.1,5,0.25),(62,470000000,0.5,5,0.5),(63,680000000,0.02,4,0.5),(64,2200000000,0.01,4,0.25),(65,2200000000,0.1,5,0.25),(66,3300000000,0.25,5,1);
/*!40000 ALTER TABLE `weerstanden` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-12 17:17:18
