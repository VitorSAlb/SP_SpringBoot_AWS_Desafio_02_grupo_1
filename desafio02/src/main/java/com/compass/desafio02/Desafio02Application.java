package com.compass.desafio02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients(basePackages = "com.compass.desafio02.feign")
@EnableJpaRepositories(basePackages = "com.compass.desafio02.domain.repositories")
@SpringBootApplication
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class Desafio02Application {

	public static void main(String[] args) {
		SpringApplication.run(Desafio02Application.class, args);
	}

}
