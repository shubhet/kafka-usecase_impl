package com.ola.driver.config;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class LocationService {

    private final AtomicReference<String> lastLocation = new AtomicReference<>("No location yet");

    public void setLastLocation(String location) {
        lastLocation.set(location);
    }

    public String getLastLocation() {
        return lastLocation.get();
    }
}
