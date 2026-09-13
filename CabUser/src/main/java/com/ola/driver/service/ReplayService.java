package com.ola.driver.service;

import com.ola.driver.config.LocationService;
import com.ola.driver.constant.Constants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Service
public class ReplayService {

    @Autowired
    private LocationService locationService;

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    public List<String> readAllFromBeginning() {
        List<String> cachedMessages = locationService.getAllSessionMessages();
        if (!cachedMessages.isEmpty()) {
            return cachedMessages;
        }

        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("group.id", "replay-" + UUID.randomUUID());
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "earliest");
        props.put("enable.auto.commit", "false");

        List<String> messages = new ArrayList<>();

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(Constants.TOPIC_NAME));

            long assignmentWaitDeadline = System.currentTimeMillis() + 5000L;
            while (System.currentTimeMillis() < assignmentWaitDeadline && consumer.assignment().isEmpty()) {
                consumer.poll(Duration.ofMillis(200));
            }

            if (!consumer.assignment().isEmpty()) {
                consumer.seekToBeginning(consumer.assignment());
            }

            long lastMessageTime = System.currentTimeMillis();
            while (System.currentTimeMillis() - lastMessageTime < 2000L || messages.isEmpty()) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                if (records.isEmpty()) {
                    if (!messages.isEmpty()) {
                        break;
                    }
                    continue;
                }

                lastMessageTime = System.currentTimeMillis();
                for (ConsumerRecord<String, String> record : records) {
                    messages.add(record.value());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }
}
