package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private final AccountDao accountDao;
    private final UserDao userDao;
    private final AccountService accountService;

    public AccountController(AccountDao accountDao, UserDao userDao, AccountService accountService) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.accountService = accountService;
    }

    @GetMapping("/me/account")
    public Account getMyAccount(Principal principal) {
        Integer userId = userDao.findIdByUsername(principal.getName());
        return accountService.getAccountByUserId(userId);
    }

    @GetMapping("/account/{userId}")
    public Account getAccount(@PathVariable int userId) {
        return accountService.getAccountByUserId(userId);
    }

    @GetMapping("/accountByAccountId/{accountId}")
    public Account getAccountByAccountId(@PathVariable int accountId) {
        return accountService.getAccountByAccountId(accountId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/account/{userId}", method = RequestMethod.PUT)
    public void update(@RequestBody Account updatedAccount, @PathVariable int userId) {
        if(!accountService.update(updatedAccount, userId)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Account with user ID %d not found", userId));
        }
    }
}
