package project_5headers.com.team_project.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project_5headers.com.team_project.service.GoogleMapService;

@RestController
@RequestMapping("/api/maps")
public class GoogleMapController {

    private final GoogleMapService googleMapService;

    public GoogleMapController(GoogleMapService googleMapService) {
        this.googleMapService = googleMapService;
    }

    @GetMapping("/geocode")
    public String getGeocode(@RequestParam String address) {
        return googleMapService.getGeocode(address);
    }
}
