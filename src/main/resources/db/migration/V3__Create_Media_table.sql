CREATE TABLE IF NOT EXISTS `media` (
  `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `title` varchar(255) NOT NULL,
  `description` TEXT,
  `cover_uri` varchar(2000),
  `media_type_id` bigint NOT NULL,
  FOREIGN KEY (`media_type_id`) REFERENCES `media_type`(`id`)
);