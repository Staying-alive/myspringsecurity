package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@MapperScan("com.example.demo.mapper")
// kaiqi renzhengshouquanzhujie, kaiqi xianhoufangwenkongzhi
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SpringsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecurityApplication.class, args);
	}
}
