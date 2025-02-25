package com.mango.mangopic;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.mango.mangopic.mapper")
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableAsync
public class MangopicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MangopicApplication.class, args);
    }

}
