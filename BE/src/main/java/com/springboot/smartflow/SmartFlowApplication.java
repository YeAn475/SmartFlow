package com.springboot.smartflow;

import org.mybatis.spring.annotation.MapperScan; // 이 임포트가 필요합니다.
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.springboot.smartflow.domain.member.mapper") // Mapper 인터페이스가 있는 패키지 경로
public class SmartFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartFlowApplication.class, args);
    }
}