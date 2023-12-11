package com.lacorp.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lacorp.backend.execption.HttpStatusErrorException;
import com.lacorp.backend.mapper.HueMapper;
import com.lacorp.backend.model.HueRepositoryModel;
import com.lacorp.backend.model.LightRepositoryModel;
import com.lacorp.backend.model.UserRepositoryModel;
import com.lacorp.backend.model.json.hue.GenericResponse;
import com.lacorp.backend.model.json.hue.LinkButtonResponse;
import com.lacorp.backend.model.json.hue.OAuthTokenResponse;
import com.lacorp.backend.model.json.hue.UsernameResponse;
import com.lacorp.backend.repository.HueRepository;
import com.lacorp.backend.repository.UserRepository;
import com.lacorp.backend.service.impl.JwtUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@Service
public class HueService {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final HueMapper hueMapper = HueMapper.INSTANCE;
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

    @Autowired
    private HueRepository hueRepository;
    @Autowired
    private JwtUserServiceImpl jwtUserService;
    @Autowired
    private UserRepository userRepository;

    public static void printResponseEntity(ResponseEntity<String> responseEntity) {
        System.out.println("StatusCode: " + responseEntity.getStatusCode());
        System.out.println("Headers: " + responseEntity.getHeaders());
        System.out.println("Body: " + responseEntity.getBody());
    }

    public String generateLink(Authentication authentication) {
        UserRepositoryModel user = (UserRepositoryModel) authentication.getPrincipal();
        String encodeJWT = jwtUserService.generateJwtForHue(user);
        String deviceId = user.getUsername()+ "deviceId";
        return String.format("%soauth2/auth?clientid=%s&appid=%s&deviceid=%s&devicename=%s&state=%s&response_type=code",
                baseUrl, clientId, appId, deviceId, deviceName, encodeJWT);
    }

    public void saveAccount(String code, String state) throws JsonProcessingException {
        hueRepository.save(getAccountInfo(code, state));
    }

    public void delete(Authentication authentication) {
        HueRepositoryModel hueRepositoryModel = hueRepository.findById(((UserRepositoryModel) authentication.getPrincipal()).getHueAccount().getId()).get();
        hueRepository.delete(hueRepositoryModel);
    }




    public String getLights(Authentication authentication) {
        UserRepositoryModel user = (UserRepositoryModel) authentication.getPrincipal();
        HueRepositoryModel hueRepositoryModel = hueRepository.findById(user.getHueAccount().getId()).get();

        String accessToken = hueRepositoryModel.getAccessToken();
        String userName = hueRepositoryModel.getUsername();
//        String url = baseUrl + "bridge/" + userName + "/lights";
        String url = baseUrl + "bridge/" + userName ;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, request, String.class);
        return response.getBody();
    }





    private HueRepositoryModel getAccountInfo(String code, String state) throws JsonProcessingException {
        OAuthTokenResponse tokenResponse = getToken(code);
        GenericResponse<UsernameResponse> usernameResponse = getUsername(tokenResponse.getAccessToken());
        long lastRefresh = System.currentTimeMillis();
        UserRepositoryModel user =(UserRepositoryModel) jwtUserService.getUserFromJwt(state);
        HueRepositoryModel hueRepositoryModel = hueMapper.toHueRepositoryModel(tokenResponse, usernameResponse.getSuccess(), lastRefresh);
        user.setHueAccount(hueRepositoryModel);
        userRepository.save(user);
        return hueRepositoryModel;
    }

    private OAuthTokenResponse getToken(String code) throws JsonProcessingException, HttpStatusErrorException {
        String url = baseUrl + "oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        headers.add("Authorization", "Basic " + encodedAuth);
        String requestBody = "code=" + code + "&state=" + state;
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.POST, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            return objectMapper.readValue(response.getBody(), OAuthTokenResponse.class);
        } else {
            throw new HttpStatusErrorException("Erreur lors de la demande du jeton, statut : " + response.getStatusCodeValue());
        }
    }

    private GenericResponse<LinkButtonResponse> pressButton(String accessToken) throws JsonProcessingException, HttpStatusErrorException {
        String url = baseUrl + "bridge/0/config";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);
        String bodyJSON = "{ \"linkbutton\" : true }";
        HttpEntity<String> request = new HttpEntity<>(bodyJSON, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.PUT, request, String.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            List<GenericResponse<LinkButtonResponse>> responseList = objectMapper.readValue(response.getBody(), new TypeReference<>() {
            });
            return responseList.get(0);
        } else {
            throw new HttpStatusErrorException("Erreur lors de la pression du bouton, statut : " + response.getStatusCodeValue());
        }
    }

    private GenericResponse<UsernameResponse> getUsername(String accessToken) throws JsonProcessingException, HttpStatusErrorException {
        if (pressButton(accessToken).getSuccess().isLinkButton()) {
            String url = baseUrl + "bridge/";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + accessToken);
            String bodyJSON = "{ \"devicetype\": \"PandaDeviceTest\" }";
            HttpEntity<String> request = new HttpEntity<>(bodyJSON, headers);
            ResponseEntity<String> response = new RestTemplate().exchange(url, HttpMethod.POST, request, String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                List<GenericResponse<UsernameResponse>> responseList = objectMapper.readValue(response.getBody(), new TypeReference<>() {
                });
                return responseList.get(0);
            } else {
                throw new HttpStatusErrorException("Erreur lors de la demande du jeton, statut : " + response.getStatusCodeValue());
            }
        } else {
            throw new HttpStatusErrorException("Erreur lors de la pression du bouton, statut : " + pressButton(accessToken).getSuccess().isLinkButton());
        }
    }



}
