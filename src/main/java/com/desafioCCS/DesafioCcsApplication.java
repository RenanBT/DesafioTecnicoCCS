package com.desafioCCS;

import java.math.BigDecimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioCcsApplication {
	
	public static BigDecimal[] allNumbers;
	
	public static void main(String[] args) {
		
		allNumbers = LoadTransform.loadAllNumbers();
		
		SpringApplication.run(DesafioCcsApplication.class, args);
		
		
		
		
	}

}
