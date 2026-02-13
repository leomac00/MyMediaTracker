CREATE TABLE IF NOT EXISTS `user_media` (
  `id_user` BIGINT(20) NOT NULL,
  `id_media` BIGINT(20) NOT NULL,
  `deleted` BOOLEAN NOT NULL DEFAULT FALSE,
  PRIMARY KEY (`id_user`, `id_media`),
  KEY `fk_user_media_media` (`id_media`),
  CONSTRAINT `fk_user_media_user`
    FOREIGN KEY (`id_user`) REFERENCES `users` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `fk_user_media_media`
    FOREIGN KEY (`id_media`) REFERENCES `media` (`id`)
    ON DELETE CASCADE
) ENGINE=InnoDB;
