package com.desafioCCS;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class LoadHttpAPI {

	@GetMapping("/load")
	public String get() {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(DesafioCcsApplication.allNumbers);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "ERROR";
			
		}
		

	}
	

}
