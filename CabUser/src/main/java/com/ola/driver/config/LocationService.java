package com.ola.driver.config;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class LocationService {

    private final AtomicReference<String> lastLocation = new AtomicReference<>("No location yet");
    private final List<String> allMessages = Collections.synchronizedList(new ArrayList<>());

    public void setLastLocation(String location) {
        lastLocation.set(location);
        allMessages.add(location);
    }

    public String getLastLocation() {
        return lastLocation.get();
    }

    public List<String> getAllMessages() {
        synchronized (allMessages) {
            return new ArrayList<>(allMessages);
        }
    }
}
