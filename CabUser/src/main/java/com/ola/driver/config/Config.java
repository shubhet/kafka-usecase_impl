package com.ola.driver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

import com.ola.driver.constant.Constants;

@Configuration
public class Config {

	@KafkaListener(topics = Constants.TOPIC_NAME, groupId = Constants.GROUP_ID)
	public void updatedLocation(String value) {

		System.out.println(value);

	}

}
