package com.wable.harmonika;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HarmonikaApplication {

	public static void main(String[] args) {
		SpringApplication.run(HarmonikaApplication.class, args);
	}

}
