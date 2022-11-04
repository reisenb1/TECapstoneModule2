package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountService {

    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public Account getAccountByUserId(int id) {
        return accountDao.getAccountByUserId(id);
    }

    public Account getAccountByAccountId(int id) {
        return accountDao.getAccountByAccountId(id);
    }

    public boolean update(Account updatedAccount, int userId) {
        return accountDao.update(updatedAccount, userId);
    }
}
