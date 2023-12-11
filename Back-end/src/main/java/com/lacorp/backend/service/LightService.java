package com.lacorp.backend.service;


import com.lacorp.backend.model.HueRepositoryModel;
import com.lacorp.backend.model.LightRepositoryModel;
import com.lacorp.backend.model.UserRepositoryModel;
import com.lacorp.backend.repository.HueRepository;
import com.lacorp.backend.repository.LightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class LightService {
    @Autowired
    private LightRepository lightRepository;
    @Autowired
    private HueRepository hueRepository;

    @Value("${api.hue.baseUrl}")
    private String baseUrl;


    public String turnOnLight(LightRepositoryModel light, Authentication authentication) {
        UserRepositoryModel user = (UserRepositoryModel) authentication.getPrincipal();
        HueRepositoryModel hueRepositoryModel = hueRepository.findById(user.getHueAccount().getId()).get();
        String accessToken = hueRepositoryModel.getAccessToken();
        String userName = hueRepositoryModel.getUsername();
        String url = baseUrl + "bridge/" + userName + "/lights/" + light.getConstructor_id() + "/state";
        String bodyJSON = "{ \"on\": true }";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> request = new HttpEntity<>(bodyJSON, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.PUT, request, String.class);
        return response.getBody();
    }

    public String turnOffLight(LightRepositoryModel light, Authentication authentication) {
        UserRepositoryModel user = (UserRepositoryModel) authentication.getPrincipal();
        HueRepositoryModel hueRepositoryModel = hueRepository.findById(user.getHueAccount().getId()).get();
        String accessToken = hueRepositoryModel.getAccessToken();
        String userName = hueRepositoryModel.getUsername();
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
