package com.ola.driver.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.ola.driver.constant.Constants;

@Component
public class ConsumerListener {

    @Autowired
    private LocationService locationService;

    @KafkaListener(topics = Constants.TOPIC_NAME, groupId = Constants.GROUP_ID)
    public void updatedLocation(String value) {
        locationService.setLastLocation(value);
        System.out.println("Updated last location: " + value);
    }
}
