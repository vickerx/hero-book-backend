CREATE TABLE `hero_story`(
    `id` bigint(20) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `title` varchar(200) NOT NULL,
    `author` varchar(50) NOT NULL,
    `content` LONG NOT NULL,
    `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
    `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
)
