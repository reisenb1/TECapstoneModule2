package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> getTransfersByUserId(int id) {
        String sql = "SELECT * FROM transfer WHERE account_from = ? OR account_to = ?";

        JdbcAccountDao jdbcAccountDao =  new JdbcAccountDao(jdbcTemplate);
        int accountId =  jdbcAccountDao.getAccountByUserId(id).getAccountId();

        List<Transfer> transfers = jdbcTemplate.query(sql, new TransferRowMapper(), accountId, accountId);
        return transfers;
    }

    @Override
    public Transfer getTransferByTransferId(int id) {
        return null;
    }

    private class TransferRowMapper implements RowMapper<Transfer> {
        @Override
        public Transfer mapRow(ResultSet resultSet, int i) throws SQLException {
            Transfer transfer = new Transfer();
            transfer.setTransferId(resultSet.getInt("transfer_id"));
            transfer.setTransferTypeId(resultSet.getInt("transfer_type_id"));
            transfer.setTransferStatusId(resultSet.getInt("transfer_status_id"));
            transfer.setAccountIdFrom(resultSet.getInt("account_from"));
            transfer.setAccountIdTo(resultSet.getInt("account_to"));
            return transfer;
        }
    }
}
