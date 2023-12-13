package com.lacorp.backend.service;


import com.lacorp.backend.model.AccountHue;
import com.lacorp.backend.model.Light;
import com.lacorp.backend.model.User;
import com.lacorp.backend.repository.HueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class LightService {
    @Autowired
    private HueRepository hueRepository;

    @Value("${api.hue.baseUrl}")
    private String baseUrl;


    public String turnOnLight(Light light, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        AccountHue accountHue = hueRepository.findById(user.getHueAccount().getId()).get();
        String accessToken = accountHue.getAccessToken();
        String userName = accountHue.getUsername();
        String url = baseUrl + "bridge/" + userName + "/lights/" + light.getConstructor_id() + "/state";
        String bodyJSON = "{ \"on\": true }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> request = new HttpEntity<>(bodyJSON, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.PUT, request, String.class);
        return response.getBody();
    }

    public String turnOffLight(Light light, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        AccountHue accountHue = hueRepository.findById(user.getHueAccount().getId()).get();
        String accessToken = accountHue.getAccessToken();
        String userName = accountHue.getUsername();
        String url = baseUrl + "bridge/" + userName + "/lights/" + light.getConstructor_id() + "/state";
        String bodyJSON = "{ \"on\": false }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> request = new HttpEntity<>(bodyJSON,headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.PUT, request, String.class);
        return response.getBody();
    }
}
