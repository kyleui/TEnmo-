package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.security.Principal;

public class RESTAccountService {

    public static String AUTH_TOKEN = "";
    private final String BASE_URL;
    private final RestTemplate restTemplate = new RestTemplate();
    public AuthenticatedUser authenticatedUser;
    private Account account = new Account();

    public RESTAccountService(String url) {
        this.BASE_URL = url;
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


    public BigDecimal getAccountBalance() {
        BigDecimal balance = null;

        try {
            balance = restTemplate.exchange(BASE_URL + "account/balance", HttpMethod.GET, makeAuthEntity(), BigDecimal.class).getBody();
        } catch (RestClientResponseException ex) {
            System.out.println(ex.getMessage());
        }
        return balance;
    }

    //from lecture code

    public User[] listUsers(String token) {
        HttpEntity entity = createAuthEntity(token);
        ResponseEntity<User[]> response = null;
        try {
            response = restTemplate.exchange(BASE_URL + "users", HttpMethod.GET, entity, User[].class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return response.getBody();
    }
}
