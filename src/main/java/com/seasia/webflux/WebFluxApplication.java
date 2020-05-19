package com.seasia.webflux;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.seasia.webflux.repository.EmployeeRepository;

@SpringBootApplication
public class WebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebFluxApplication.class, args);
	}

	@Bean
	CommandLineRunner employees(EmployeeRepository employeeRepository) {

		System.out.println("Clearing data....");
		return args -> {
			employeeRepository.deleteAll().subscribe();
		};
	}

}
