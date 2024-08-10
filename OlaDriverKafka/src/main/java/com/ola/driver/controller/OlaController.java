package com.ola.driver.controller;

import com.ola.driver.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/ola")
public class OlaController {

	@Autowired
	private KafkaService kafkaService;

	@PostMapping("/updateLocation")
	public ResponseEntity<?> updateLocation() {

		this.kafkaService.updateLocation("Cab Location is upadted now" + " : " + "[ " + Math.round(Math.random() * 100)
				+ " , " + Math.round(Math.random() * 100) + " " + "]");

		return new ResponseEntity<>(Map.of("message", "Location updated by cab user"), HttpStatus.OK);
	}

}
