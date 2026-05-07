package com.ppfvp.ppfvp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PpfvpApplication {

	public static void main(String[] args) {
		SpringApplication.run(PpfvpApplication.class, args);
	}

}
