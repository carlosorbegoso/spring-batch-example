package com.skyblue.apiconsultas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


import java.util.Collections;

@SpringBootApplication
@EnableCaching
public class ApiConsultasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiConsultasApplication.class, args);
	}


}
