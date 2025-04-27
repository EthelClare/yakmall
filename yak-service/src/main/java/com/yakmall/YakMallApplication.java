package com.yakmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.yakmall.mapper")
@SpringBootApplication
public class YakMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(YakMallApplication.class, args);
    }
}