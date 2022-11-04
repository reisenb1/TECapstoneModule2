package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    public User[] getAllUsers () {
        String url = String.format("%susers", API_BASE_URL);

        ResponseEntity<User[]> response = restTemplate.exchange(url, HttpMethod.GET,
                makeAuthEntity(), User[].class);
        User[] users = response.getBody();

        return users;
    }

    public Account getMyAccount() {
        String url = String.format("%sme/account", API_BASE_URL);

        ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET,
                makeAuthEntity(), Account.class);
        Account account = response.getBody();

        return account;
    }

    public Account getAccount(int userId) {
        String url = String.format("%s/account/%d", API_BASE_URL, userId);

        ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET,
                makeAuthEntity(), Account.class);
        Account account = response.getBody();

        return account;
    }

    public Account getAccountByAccountId(int accountId) {
        String url = String.format("%s/accountByAccountId/%d", API_BASE_URL, accountId);

        ResponseEntity<Account> response = restTemplate.exchange(url, HttpMethod.GET,
                makeAuthEntity(), Account.class);
        Account account = response.getBody();

        return account;
    }



    public void updateAccount(Account updatedAccount) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Account> entity =
                new HttpEntity<>(updatedAccount, headers);

        ResponseEntity<String> response = restTemplate.exchange(API_BASE_URL + "account/" +
                updatedAccount.getUserId(), HttpMethod.PUT, entity, String.class);

        response.getStatusCode();
    }

    public void createTransfer(Transfer newTransfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<Transfer> entity = new HttpEntity<Transfer>(newTransfer, headers);

        Transfer savedTransfer =
                restTemplate.postForObject(API_BASE_URL + "transfers", entity, Transfer.class);
    }

    public List<Transfer> getMyPastTransfers() {
        String url = String.format("%stransfers", API_BASE_URL);

        ResponseEntity<Transfer[]> response = restTemplate.exchange(url, HttpMethod.GET,
                makeAuthEntity(), Transfer[].class);
        Transfer[] transfers = response.getBody();

        Account myAccount = getMyAccount();

        List<Transfer> transferList = new ArrayList<>();
        for (Transfer transfer : transfers) {
            if (transfer.getAccountIdFrom() == myAccount.getAccountId() ||
                    transfer.getAccountIdTo() == myAccount.getAccountId()) {
                transferList.add(transfer);
            }
        }
        return transferList;
    }

    public HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
