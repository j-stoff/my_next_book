CREATE DATABASE /*!32312 IF NOT EXISTS*/ `ml_6` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `ml_6`;

--
-- Table structure for table `authors`
--

DROP TABLE IF EXISTS `authors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authors` (
  `id_author` int(11) NOT NULL,
  `author_first_name` varchar(64) NOT NULL,
  `author_last_name` varchar(64) NOT NULL,
  `author_genre` varchar(256) DEFAULT NULL,
  `author_average_rating` double NOT NULL,
  PRIMARY KEY (`id_author`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `books` (
  `id_books` int(11) NOT NULL AUTO_INCREMENT,
  `book_title` varchar(256) NOT NULL,
  `book_author` varchar(128) NOT NULL,
  `book_rating` double NOT NULL,
  `book_num_rating` int(11) DEFAULT NULL,
  `book_num_review` int(11) DEFAULT NULL,
  `book_genre` varchar(45) NOT NULL,
  `fk_id_author` int(11) NOT NULL,
  `book_isbn` varchar(10) NOT NULL,
  `book_goodreads_id` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id_books`),
  UNIQUE KEY `book_title_UNIQUE` (`book_title`),
  KEY `fk_id_authors_idx` (`fk_id_author`),
  CONSTRAINT `fk_id_authors` FOREIGN KEY (`fk_id_author`) REFERENCES `authors` (`id_author`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_name` varchar(25) NOT NULL,
  `role_type` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES ('admin','administrator');
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(25) NOT NULL,
  `user_password` varchar(30) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','admin',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
