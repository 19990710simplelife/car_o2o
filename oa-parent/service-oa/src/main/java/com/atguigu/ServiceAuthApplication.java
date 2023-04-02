package com.atguigu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @ClassName ServiceAuthApplication
 * @Description
 * @Author simplelife
 * @Date 2023/3/29 22:48
 * @Version 1.0
 */
@SpringBootApplication
@MapperScan("com.atguigu.*.mapper")
class ServiceAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class, args);
    }
}
