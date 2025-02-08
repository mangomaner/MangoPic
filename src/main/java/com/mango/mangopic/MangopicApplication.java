package com.mango.mangopic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.mango.mangopic.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
public class MangopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangopicApplication.class, args);
    }

}
