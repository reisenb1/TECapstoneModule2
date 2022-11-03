package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TenmoService {

    private static final String API_BASE_URL = "http://localhost:8080/";
    private final RestTemplate restTemplate = new RestTemplate();

    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public BigDecimal getBalance() {
        String url = String.format("%sme/account", API_BASE_URL);

        ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET,
                makeAuthEntity(), Account.class);
        Account account = response.getBody();

        return account.getBalance();
    }

    protected HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
