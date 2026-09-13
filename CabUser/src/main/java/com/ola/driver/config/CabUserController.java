package com.ola.driver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ola.driver.service.ReplayService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cab")
public class CabUserController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private ReplayService replayService;

    @GetMapping("/location")
    public Map<String, String> getLocation() {
        return Map.of("lastLocation", locationService.getLastLocation());
    }

    @GetMapping("/messages")
    public List<String> getAllMessagesFromBeginning() {
        return replayService.readAllFromBeginning();
    }
}
