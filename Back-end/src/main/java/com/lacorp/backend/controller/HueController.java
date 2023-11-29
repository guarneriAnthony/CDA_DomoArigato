package com.lacorp.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lacorp.backend.model.HueAccountInputDTO;
import com.lacorp.backend.service.HueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@RestController
@RequestMapping(path = "Hue")
public class HueController {

    @Autowired
    private HueService hueService;

    @Value("${api.hue.baseUrl}")
    private String baseUrl;
    @Value("${api.hue.clientId}")
    private String clientId;
    @Value("${api.hue.clientSecret}")
    private String clientSecret;
    @Value("${api.hue.appId}")
    private String appId;
    @Value("${api.hue.deviceName}")
    private String deviceName;
    @Value("${api.hue.state}")
    private String state;

    //Envoie sur la page qui redirige pour récupérer le code
    @GetMapping("OAuth/redirect")
    public String generateLink(@RequestParam("deviceId") String deviceId) {
        return baseUrl + "oauth2/auth?" +
                "clientid=" + clientId +
                "&appid=" + appId +
                "&deviceid=" + deviceId +
                "&devicename=" + deviceName +
                "&state=approved" +
                "&response_type=code";
    }

    @GetMapping("OAuth/callback")
    private boolean callback(@RequestParam("code") String code) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        ResponseEntity<String> tokenEntity = getToken(code);
        String token = tokenEntity.getBody();

        String accessToken = objectMapper.readTree(token).get("access_token").asText();
        String refreshToken = objectMapper.readTree(token).get("refresh_token").asText();
        String tokenType = objectMapper.readTree(token).get("token_type").asText();
        String access_token_expires_in = objectMapper.readTree(token).get("access_token_expires_in").asText();
        String refresh_token_expires_in = objectMapper.readTree(token).get("refresh_token_expires_in").asText();

        pressButton(accessToken);

        // vérifier Username
        ResponseEntity<String> usernameEntity =addDevice(accessToken);
        String userName = usernameEntity.getBody();
        userName = objectMapper.readTree(userName).get("username").asText();

        // HueAccountInputDTO hueAccountInputDTO = new HueAccountInputDTO();
        // hueAccountInputDTO.setUsername(userName);
        // hueAccountInputDTO.setAccessToken(accessToken);
        // hueAccountInputDTO.setRefreshToken(refreshToken);
        // hueAccountInputDTO.setTokenType(tokenType);
        // hueAccountInputDTO.setAccess_token_expires_in(access_token_expires_in);
        // hueAccountInputDTO.setRefresh_token_expires_in(refresh_token_expires_in);


        // hueService.saveAccount(hueAccountInputDTO);

        return true;
    }

    private ResponseEntity<String> getToken(String code) {
        String url = baseUrl + "oauth2/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.add("Authorization", "Basic " + encodedAuth);

        String requestBody = "code=" + code + "&state=" + state;

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        return new RestTemplate().exchange(url, HttpMethod.POST, request, String.class);
    }

    private void pressButton(String accessToken) {
        String url = baseUrl + "bridge/0/config";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);

        String bodyJSON = "{ \"linkbutton\" : true }";

        HttpEntity<String> request = new HttpEntity<>(bodyJSON, headers);

        new RestTemplate().exchange(url, HttpMethod.PUT, request, String.class);
    }

    private ResponseEntity<String> addDevice(String accessToken) {
        String url = baseUrl + "bridge/";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.add("Authorization", "Bearer " + accessToken);

        // Le nom du dispositif doit être dans un tableau JSON
        String bodyJSON = "{ \"devicetype\": \"PandaDeviceTest\" }";

        HttpEntity<String> request = new HttpEntity<>(bodyJSON, headers);

       return new RestTemplate().exchange(url, HttpMethod.POST, request, String.class);
    }
}
