package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    List<Account> findAll();

    Account getAccountByUserId(int id);

    Account getAccountByAccountId(int id);

    @Transactional
    boolean update(Account updatedAccount, int userId);

    Account findByUsername(String username);

    int findAccountIdByUsername(String username);

}
