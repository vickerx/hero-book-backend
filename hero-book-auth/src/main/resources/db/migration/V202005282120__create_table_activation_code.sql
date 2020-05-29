CREATE TABLE `activation_code`(
    `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `activation_code` varchar(32),
    `expired_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `user_id` bigint(20) NOT NULL,
    UNIQUE KEY `unique_activation_code` (`activation_code`),
    CONSTRAINT `activation_code_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
)
