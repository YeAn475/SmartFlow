package com.springboot.smartflow;

import org.mybatis.spring.annotation.MapperScan; // 이 임포트가 필요합니다.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.springboot.smartflow.domain.**.mapper")
public class SmartFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartFlowApplication.class, args);
    }
}