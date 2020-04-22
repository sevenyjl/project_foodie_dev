package com.seven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.seven.dao")
public class ProjectFoodieDevApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectFoodieDevApplication.class, args);
    }

}
