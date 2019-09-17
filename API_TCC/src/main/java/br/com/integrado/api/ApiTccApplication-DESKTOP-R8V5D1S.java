package br.com.integrado.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("br.com.integrado.api")
public class ApiTccApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ApiTccApplication.class, args);
	}
	
}
