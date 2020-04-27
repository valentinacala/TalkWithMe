-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Creato il: Mag 13, 2018 alle 14:34
-- Versione del server: 5.7.19
-- Versione PHP: 5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `applicationdata`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `questions`
--

DROP TABLE IF EXISTS `questions`;
CREATE TABLE IF NOT EXISTS `questions` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cathegory_id` int(10) UNSIGNED NOT NULL,
  `text` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_questions_question_categories1_idx` (`cathegory_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `questions`
--

INSERT INTO `questions` (`id`, `cathegory_id`, `text`, `created_at`, `updated_at`) VALUES
(1, 1, 'have you got difficulties in using the device?', '2017-12-01 12:32:32', '2017-12-01 12:32:32'),
(2, 2, 'how are you today?', '2017-12-01 12:34:07', '2017-12-01 12:34:07'),
(3, 1, 'how much do you have practice with the device?', '2017-12-01 12:34:07', '2017-12-01 12:34:07'),
(4, 2, 'how much attention he/she paid to your activity?', '2017-12-01 12:36:05', '2017-12-01 12:36:05');

-- --------------------------------------------------------

--
-- Struttura della tabella `question_answers`
--

DROP TABLE IF EXISTS `question_answers`;
CREATE TABLE IF NOT EXISTS `question_answers` (
  `relationship_id` int(10) UNSIGNED NOT NULL,
  `question_id` int(10) UNSIGNED NOT NULL,
  `answer` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `udated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `fk_question_answers_questions1_idx` (`question_id`),
  KEY `fk_question_answers_relationships1_idx` (`relationship_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Struttura della tabella `question_categories`
--

DROP TABLE IF EXISTS `question_categories`;
CREATE TABLE IF NOT EXISTS `question_categories` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cathegory_definition` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `question_categories`
--

INSERT INTO `question_categories` (`id`, `cathegory_definition`, `created_at`, `updated_at`) VALUES
(1, 'device usage', '2017-12-01 12:31:40', '2017-12-01 12:31:40'),
(2, 'user feelings', '2017-12-01 12:31:40', '2017-12-01 12:31:40');

-- --------------------------------------------------------

--
-- Struttura della tabella `relationships`
--

DROP TABLE IF EXISTS `relationships`;
CREATE TABLE IF NOT EXISTS `relationships` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `subject_id` int(10) UNSIGNED NOT NULL,
  `user_id` int(10) UNSIGNED NOT NULL,
  `survey_id` int(10) UNSIGNED DEFAULT NULL,
  `relationship_description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_relationships_users1_idx` (`user_id`),
  KEY `fk_relationships_subjects1_idx` (`subject_id`),
  KEY `fk_relationships_survey_types1_idx` (`survey_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `relationships`
--

INSERT INTO `relationships` (`id`, `subject_id`, `user_id`, `survey_id`, `relationship_description`, `created_at`, `updated_at`) VALUES
(1, 3, 3, 1, 'Medical Doctor which follows Gianpaolo from 3 years ago. They meet every Monday.', '2017-12-01 12:58:50', '2017-12-01 13:16:26'),
(2, 1, 2, NULL, 'Mom of Giulio', '2017-12-01 12:58:50', '2017-12-01 12:58:50'),
(3, 1, 3, NULL, 'Giulio\'s medical doctor, she suggested the first approach to ATA', '2017-12-01 21:13:13', '2017-12-01 21:13:13');

-- --------------------------------------------------------

--
-- Struttura della tabella `subjects`
--

DROP TABLE IF EXISTS `subjects`;
CREATE TABLE IF NOT EXISTS `subjects` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_id` int(10) UNSIGNED NOT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mobile` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_subjects_subject_categories1_idx` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `subjects`
--

INSERT INTO `subjects` (`id`, `category_id`, `first_name`, `last_name`, `email`, `phone_number`, `mobile`, `created_at`, `updated_at`) VALUES
(1, 3, 'Giulio', 'Verdi', NULL, NULL, NULL, '2017-12-01 12:48:29', '2017-12-01 12:48:29'),
(2, 1, 'Marinella', 'Verdi', NULL, NULL, NULL, '2017-12-01 12:48:29', '2017-12-01 12:48:29'),
(3, 4, 'Gianpaolo', 'Aranci', NULL, NULL, NULL, '2017-12-01 12:48:59', '2017-12-01 12:48:59');

-- --------------------------------------------------------

--
-- Struttura della tabella `subject_categories`
--

DROP TABLE IF EXISTS `subject_categories`;
CREATE TABLE IF NOT EXISTS `subject_categories` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_definition` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `subject_categories`
--

INSERT INTO `subject_categories` (`id`, `category_definition`, `created_at`, `updated_at`) VALUES
(1, 'tablet_user', '2017-12-01 12:36:32', '2017-12-01 12:36:32'),
(2, '20icon_user', '2017-12-01 12:36:32', '2017-12-01 12:36:32'),
(3, '5icon_user', '2017-12-01 12:37:05', '2017-12-01 12:37:05'),
(4, 'VOCA_user', '2017-12-01 12:37:05', '2017-12-01 12:37:05');

-- --------------------------------------------------------

--
-- Struttura della tabella `surveys`
--

DROP TABLE IF EXISTS `surveys`;
CREATE TABLE IF NOT EXISTS `surveys` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `survey_description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `surveys`
--

INSERT INTO `surveys` (`id`, `survey_description`, `created_at`, `updated_at`) VALUES
(1, 'Giampaolo\'s personalized survey', '2017-12-01 13:00:29', '2017-12-01 13:00:29'),
(2, 'survey about VOCA usage', '2017-12-01 13:00:29', '2017-12-01 13:00:29');

-- --------------------------------------------------------

--
-- Struttura della tabella `survey_questions`
--

DROP TABLE IF EXISTS `survey_questions`;
CREATE TABLE IF NOT EXISTS `survey_questions` (
  `survey_id` int(10) UNSIGNED NOT NULL,
  `question_id` int(10) UNSIGNED NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  KEY `fk_survey_questions_questions1_idx` (`question_id`),
  KEY `fk_survey_questions_surveys1_idx` (`survey_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dump dei dati per la tabella `survey_questions`
--

INSERT INTO `survey_questions` (`survey_id`, `question_id`, `created_at`, `updated_at`) VALUES
(2, 1, '2017-12-01 13:06:05', '2017-12-01 13:06:05'),
(2, 2, '2017-12-01 13:06:05', '2017-12-01 13:06:05'),
(2, 3, '2017-12-01 13:08:59', '2017-12-01 13:08:59'),
(1, 1, '2017-12-01 13:09:36', '2017-12-01 13:09:36'),
(1, 2, '2017-12-01 13:09:36', '2017-12-01 13:09:36'),
(1, 4, '2017-12-01 13:10:27', '2017-12-01 13:10:27'),
(1, 3, '2017-12-01 13:10:27', '2017-12-01 13:10:27');

-- --------------------------------------------------------

--
-- Struttura della tabella `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_id` int(10) UNSIGNED NOT NULL,
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `first_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `mobile` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `phone_number` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `remember_token` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_users_categories1_idx` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `users`
--

INSERT INTO `users` (`id`, `category_id`, `username`, `password`, `first_name`, `last_name`, `mobile`, `email`, `phone_number`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 4, 'Mario.Rossi', 'password', 'Mario', 'Rossi', '345675654', 'mario.rossi@gmail.com', NULL, NULL, '2017-12-01 12:40:04', '2017-12-01 12:40:04'),
(2, 3, 'Paola.Verdi', 'password', 'Paola', 'Verdi', '4329836452', NULL, NULL, NULL, '2017-12-01 12:40:45', '2017-12-01 12:40:45'),
(3, 4, 'Maria.Rossi', 'password', 'Maria', 'Rossi', '3332194821', NULL, NULL, NULL, '2017-12-01 12:42:10', '2017-12-01 12:42:10'),
(4, 3, 'Francesco.Verdi', 'password', 'Francesco', 'Verdi', '32383465255', NULL, NULL, NULL, '2017-12-01 12:43:21', '2017-12-01 12:43:21');

-- --------------------------------------------------------

--
-- Struttura della tabella `user_categories`
--

DROP TABLE IF EXISTS `user_categories`;
CREATE TABLE IF NOT EXISTS `user_categories` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `category_definition` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dump dei dati per la tabella `user_categories`
--

INSERT INTO `user_categories` (`id`, `category_definition`, `created_at`, `updated_at`) VALUES
(1, 'teacher', '2017-12-01 12:37:25', '2017-12-01 12:37:25'),
(2, 'psychologist', '2017-12-01 12:37:25', '2017-12-01 12:38:43'),
(3, 'caregiver', '2017-12-01 12:38:02', '2017-12-01 12:38:02'),
(4, 'medical_doctor', '2017-12-01 12:38:02', '2017-12-01 12:38:02');

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `questions`
--
ALTER TABLE `questions`
  ADD CONSTRAINT `fk_questions_question_categories1` FOREIGN KEY (`cathegory_id`) REFERENCES `question_categories` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `question_answers`
--
ALTER TABLE `question_answers`
  ADD CONSTRAINT `fk_question_answers_questions1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_question_answers_relationships1` FOREIGN KEY (`relationship_id`) REFERENCES `relationships` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `relationships`
--
ALTER TABLE `relationships`
  ADD CONSTRAINT `fk_relationships_subjects1` FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_relationships_survey_types1` FOREIGN KEY (`survey_id`) REFERENCES `surveys` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_relationships_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `subjects`
--
ALTER TABLE `subjects`
  ADD CONSTRAINT `fk_subjects_subject_categories1` FOREIGN KEY (`category_id`) REFERENCES `subject_categories` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `survey_questions`
--
ALTER TABLE `survey_questions`
  ADD CONSTRAINT `fk_survey_questions_questions1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_survey_questions_surveys1` FOREIGN KEY (`survey_id`) REFERENCES `surveys` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Limiti per la tabella `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `fk_users_categories1` FOREIGN KEY (`category_id`) REFERENCES `user_categories` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
