package com.lacorp.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lacorp.backend.execption.HttpStatusErrorException;
import com.lacorp.backend.execption.UnauthorizedException;
import com.lacorp.backend.mapper.HueMapper;
import com.lacorp.backend.model.AccountHue;
import com.lacorp.backend.model.User;
import com.lacorp.backend.model.json.hue.GenericResponse;
import com.lacorp.backend.model.json.hue.LinkButtonResponse;
import com.lacorp.backend.model.json.hue.OAuthTokenResponse;
import com.lacorp.backend.model.json.hue.UsernameResponse;
import com.lacorp.backend.repository.HueRepository;
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


    public static void printResponseEntity(ResponseEntity<String> responseEntity) {
        System.out.println("StatusCode: " + responseEntity.getStatusCode());
        System.out.println("Headers: " + responseEntity.getHeaders());
        System.out.println("Body: " + responseEntity.getBody());
    }

    public String generateLink(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        String state = jwtUserService.generateJwtForHue(user);

        String deviceId = user.getUsername() + "deviceId";

        return String.format("%soauth2/auth?" +
                        "clientid=%s" +
                        "&appid=%s" +
                        "&deviceid=%s" +
                        "&devicename=%s" +
                        "&state=%s" +
                        "&response_type=code",
                baseUrl, clientId, appId, deviceId, deviceName, state);
    }

    public void saveAccount(String code, String state) throws JsonProcessingException, UnauthorizedException {
        User user = (User) jwtUserService.getUserFromJwt(state);
        if (user == null) {
            throw new UnauthorizedException();
        }

        OAuthTokenResponse tokenResponse = getToken(code);
        GenericResponse<UsernameResponse> usernameResponse = getUsername(tokenResponse.getAccessToken());
        long lastRefresh = System.currentTimeMillis();

        AccountHue accountHue = hueMapper.toAccountHueRepositoryModel(tokenResponse, usernameResponse.getSuccess(), lastRefresh);
        user.setHueAccount(accountHue);

        jwtUserService.updateUser(user);
    }

    public void delete(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        hueRepository.delete(user.getHueAccount());
    }

    private OAuthTokenResponse getToken(String code) throws JsonProcessingException, HttpStatusErrorException {
        String url = baseUrl + "oauth2/token";

        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        String requestBody = "code=" + code + "&state=" + state;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Basic " + encodedAuth);

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

        String bodyJSON = "{ \"linkbutton\" : true }";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);

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

            String bodyJSON = "{ \"devicetype\": \"PandaDeviceTest\" }";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer " + accessToken);

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
