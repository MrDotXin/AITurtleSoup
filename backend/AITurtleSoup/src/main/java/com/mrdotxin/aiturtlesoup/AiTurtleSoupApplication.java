package com.mrdotxin.aiturtlesoup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mrdotxin.aiturtlesoup.mapper")
public class AiTurtleSoupApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiTurtleSoupApplication.class, args);
    }

}
