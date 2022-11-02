package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.services.AccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Account getAccount(Principal principal) {
        Integer userId = userDao.findIdByUsername(principal.getName());
        return accountService.getAccountByUserId(userId);
    }
}
