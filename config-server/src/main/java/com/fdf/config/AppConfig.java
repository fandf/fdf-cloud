package com.fdf.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fandongfeng
 * @date 2022/11/27 21:40
 */
@SpringBootApplication
@MapperScan("com.fdf.config.mapper")
public class AppConfig {

    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }

}
