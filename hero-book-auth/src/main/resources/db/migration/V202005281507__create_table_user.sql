CREATE TABLE `user`(
    `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `username` varchar(20) NOT NULL,
    `password` varchar(255) NOT NULL,
    `email` varchar(255) NOT NULL,
    `is_activated` tinyint(1) DEFAULT '0',
    UNIQUE KEY `unique_email` (`email`)
)
