package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDto;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class RESTTransferService {

    private final String BASE_URL;
    public static String AUTH_TOKEN = "";
    private final RestTemplate restTemplate = new RestTemplate();
    public AuthenticatedUser authenticatedUser;
    public TransferDto transferDto = new TransferDto();

    public RESTTransferService(String url) {
        this.BASE_URL = url;
    }

    public Transfer sendTransfer(TransferDto transferDto) throws TransferServiceException {
        Transfer confirmation = null;
        try {
            confirmation = restTemplate.exchange(BASE_URL + "transfer/create", HttpMethod.POST, makeAuthTransferDto(transferDto), Transfer.class).getBody();

            return confirmation;
        } catch (RestClientResponseException ex) {
            throw new TransferServiceException(
                    ex.getRawStatusCode() + " : " + ex.getResponseBodyAsString());
        } catch (ResourceAccessException ex) {
            throw new TransferServiceException(ex.getMessage());
        }
    }

    private HttpEntity makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(headers);
        return entity;
    }

    private HttpEntity makeAuthTransferDto(TransferDto transferDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        HttpEntity entity = new HttpEntity<>(transferDto, headers);
        return entity;
    }


    public Transfer[] listUserTransfers(int accountId) {
        HttpEntity entity = makeAuthEntity();
        ResponseEntity<Transfer[]> response = null;
        try {
            response = restTemplate.exchange(BASE_URL + "transfers/" + accountId, HttpMethod.GET, entity, Transfer[].class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return response.getBody();
    }

}
