package com.dnia.eaas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EssasApplication {

	public static void main(String[] args) {
		SpringApplication.run(EssasApplication.class, args);
	}

}
