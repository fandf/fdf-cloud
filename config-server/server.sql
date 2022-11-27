CREATE TABLE `config`
(
    `id`   int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `md5`  varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `config_info`
(
    `id`        int NOT NULL AUTO_INCREMENT,
    `key`       varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `value`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `config_id` int                                     DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;