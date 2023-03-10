package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> findAll() {
        return null;
    }

    @Override
    public Account getAccountByUserId(int id) {
        String sql = "SELECT * FROM account WHERE user_id = ?";

        List<Account> accounts = jdbcTemplate.query(sql, new AccountRowMapper(), id);

        if (accounts.size() > 0) {
            return accounts.get(0);
        }

        return null;
    }

    @Override
    public Account getAccountByAccountId(int id) {
        String sql = "SELECT * FROM account WHERE account_id = ?";

        List<Account> accounts = jdbcTemplate.query(sql, new AccountRowMapper(), id);

        if (accounts.size() > 0) {
            return accounts.get(0);
        }

        return null;
    }

    @Override
    @Transactional
    public boolean update(Account updatedAccount, int userId) {
        String sql = "UPDATE account SET account_id = ?, user_id = ?, balance = ? WHERE user_id = ?";

        if (jdbcTemplate.update(sql, updatedAccount.getAccountId(), updatedAccount.getUserId(),
                updatedAccount.getBalance(), updatedAccount.getUserId()) > 0) {
            return true;
        }

        return false;
    }

    @Override
    public Account findByUsername(String username) {
        return null;
    }

    @Override
    public int findAccountIdByUsername(String username) {
        return 0;
    }


    private class AccountRowMapper implements RowMapper<Account> {
        @Override
        public Account mapRow(ResultSet resultSet, int i) throws SQLException {
            Account account = new Account();
            account.setAccountId(resultSet.getInt("account_id"));
            account.setUserId(resultSet.getInt("user_id"));
            account.setBalance(resultSet.getBigDecimal("balance"));
            return account;
        }
    }
}
