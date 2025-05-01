package com.yakmall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@MapperScan("com.yakmall.mapper")
@SpringBootApplication
@ComponentScan({"com.yakmall", "com.yak.common"})
public class YakMallApplication {
    public static void main(String[] args) {
        SpringApplication.run(YakMallApplication.class, args);
    }
}