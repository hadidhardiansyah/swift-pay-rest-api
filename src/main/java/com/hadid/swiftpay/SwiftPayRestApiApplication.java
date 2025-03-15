package com.hadid.swiftpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SwiftPayRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SwiftPayRestApiApplication.class, args);
	}

}
