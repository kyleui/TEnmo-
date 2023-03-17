package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.util.List;

public class RESTUserService {


    private final String BASE_URL;
    private AuthenticatedUser authenticatedUser;
    public static String AUTH_TOKEN = "";
    public User user = new User();
    private final RestTemplate restTemplate = new RestTemplate();


    public RESTUserService(String url, AuthenticatedUser authenticatedUser) {
        this.BASE_URL = url;
        this.authenticatedUser = authenticatedUser;
    }


    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity createAuthEntity(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }
















}