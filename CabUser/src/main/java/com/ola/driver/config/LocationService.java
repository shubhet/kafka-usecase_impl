package com.ola.driver.config;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class LocationService {

    private final AtomicReference<String> lastLocation = new AtomicReference<>("No location yet");
    private final List<String> allSessionMessages = Collections.synchronizedList(new ArrayList<>());
    private final List<String> currentSessionMessages = Collections.synchronizedList(new ArrayList<>());
    private final AtomicBoolean currentSessionActive = new AtomicBoolean(false);

    public void setLastLocation(String location) {
        lastLocation.set(location);
        allSessionMessages.add(location);

        if (currentSessionActive.get()) {
            currentSessionMessages.add(location);
        }
    }

    public String getLastLocation() {
        return lastLocation.get();
    }

    public List<String> getAllSessionMessages() {
        synchronized (allSessionMessages) {
            return new ArrayList<>(allSessionMessages);
        }
    }

    public List<String> getCurrentSessionMessages() {
        synchronized (currentSessionMessages) {
            return new ArrayList<>(currentSessionMessages);
        }
    }

}
