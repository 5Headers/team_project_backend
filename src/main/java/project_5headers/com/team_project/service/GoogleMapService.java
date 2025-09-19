package project_5headers.com.team_project.service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class GoogleMapService {

    private final RestTemplate restTemplate;
    @Value("${google.maps.api-key}")
    private String apiKey;

    public GoogleMapService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public String getGeocode(String address) {
        String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + encodedAddress + "&key=" + apiKey;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }
}
