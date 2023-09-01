package com.sparta.springauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// 기술지원 Bean 수동 등록
@SpringBootApplication // Spring Security 인증 기능 제외
@EnableJpaRepositories("com.sparta.springauth.repository")
@EntityScan("com.sparta.springauth.entity")
public class SpringAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuthApplication.class, args);
    }

}