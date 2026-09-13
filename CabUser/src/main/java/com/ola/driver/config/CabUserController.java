package com.ola.driver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cab")
public class CabUserController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/location")
    public Map<String, String> getLocation() {
        return Map.of("lastLocation", locationService.getLastLocation());
    }

    @GetMapping("/getCurrentSessionMessages")
    public List<String> getCurrentSessionMessages() {
        return locationService.getCurrentSessionMessages();
    }

    @GetMapping("/loadWholeSessionData")
    public List<String> loadWholeSessionData() {
        return locationService.getAllSessionMessages();
    }

}
