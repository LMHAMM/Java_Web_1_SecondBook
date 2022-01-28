package com.lmh.secondhandbook;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.lmh.secondhandbook.mapper")
public class SecondHandBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondHandBookApplication.class, args);
    }

}
