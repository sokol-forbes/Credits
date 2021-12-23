CREATE DATABASE IF NOT EXISTS `bank`;
USE `bank`;

SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE IF NOT EXISTS `personal_data` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `surname` varchar(45) DEFAULT NULL,
  `thirdname` varchar(45) DEFAULT '----------',
  `age` int DEFAULT '18',
  `gender` varchar(45) DEFAULT 'UNDEFINED',
  `pass_number` varchar(45) DEFAULT NULL,
  `iden_number` varchar(45) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `date_of_issue` date DEFAULT NULL,
  `date_of_expirity` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `history_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_id` bigint DEFAULT NULL,
  `entrance` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `log_account_id_idx` (`account_id`),
  CONSTRAINT `FK_history_account` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `currency` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `generic_symbol` varchar(3) DEFAULT NULL,
  `symbol` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `credit_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `percent_rate` decimal(3,1) DEFAULT NULL,
  `term` int DEFAULT NULL,
  `loan_type` varchar(45) DEFAULT NULL,
  `providing_way` varchar(45) DEFAULT NULL,
  `security` varchar(45) DEFAULT NULL,
  `author` varchar(45) DEFAULT 'bank',
  `icon_url` varchar(180) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `contract` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(9,2) DEFAULT NULL,
  `responsible_id` bigint DEFAULT NULL,
  `credit_info_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `date_of_signing` date DEFAULT NULL,
  `currency_id` bigint DEFAULT NULL,
  `state` varchar(45) DEFAULT 'На рассмотрении',
  PRIMARY KEY (`id`),
  KEY `responsible_idfk_idx` (`responsible_id`,`user_id`),
  KEY `user_idfk_idx` (`user_id`),
  KEY `credit_info_idfk_idx` (`credit_info_id`),
  KEY `currency_idfk_idx` (`currency_id`),
  CONSTRAINT `credit_info_idfk` FOREIGN KEY (`credit_info_id`) REFERENCES `credit_info` (`id`),
  CONSTRAINT `currency_idfk` FOREIGN KEY (`currency_id`) REFERENCES `currency` (`id`),
  CONSTRAINT `responsible_idfk` FOREIGN KEY (`responsible_id`) REFERENCES `account` (`id`),
  CONSTRAINT `user_idfk` FOREIGN KEY (`user_id`) REFERENCES `account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `personal_id` bigint DEFAULT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'USER',
  `is_blocked` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  KEY `personal_data_idfk_idx` (`personal_id`),
  CONSTRAINT `pasport_idfk` FOREIGN KEY (`personal_id`) REFERENCES `personal_data` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `account` (login, password, role) VALUES
("1","1","ADMIN"),
("2","2","USER"),
("3","3","GUEST");

INSERT INTO `bank`.`currency` (`name`, `generic_symbol`, `symbol`) VALUES ('Dollars', 'USD', '$');
INSERT INTO `bank`.`currency` (`name`, `generic_symbol`, `symbol`) VALUES ('Euro', 'EUR', '€');
INSERT INTO `bank`.`currency` (`name`, `generic_symbol`, `symbol`) VALUES ('Russian Rubles', 'RUB', '₽');
INSERT INTO `bank`.`currency` (`name`, `generic_symbol`, `symbol`) VALUES ('Rubles', 'BLR', 'р');

INSERT INTO `bank`.`credit_info` (`name`, `percent_rate`, `term`, `loan_type`, `providing_way`, `security`, `author`) VALUES ('Ипотека приоритет', '5', '240', 'Потребительский', 'наличными', 'Поручительство', 'bank');
INSERT INTO `bank`.`credit_info` (`name`, `percent_rate`, `term`, `loan_type`, `providing_way`, `security`, `author`) VALUES ('Выше', '22', '36', 'На недвижимость', 'безналичный', 'Залог', 'bank');
INSERT INTO `bank`.`credit_info` (`name`, `percent_rate`, `term`, `loan_type`, `providing_way`, `security`, `author`) VALUES ('Оптимальный вариант', '11', '24', 'На недвижимость', 'безналичный', 'Неустойка', 'bank');
INSERT INTO `bank`.`credit_info` (`name`, `percent_rate`, `term`, `loan_type`, `providing_way`, `security`, `author`) VALUES ('Гаити', '8', '12', 'Потребительский', 'наличными', 'Страхование', 'bank');
