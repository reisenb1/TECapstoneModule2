package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Transfer> getAllToTransfers() {
        String sql = "SELECT * FROM transfer JOIN account ON transfer.account_to = account.account_id" +
                " JOIN tenmo_user ON (account.user_id = tenmo_user.user_id) WHERE transfer_type_id = 2;";

        List<Transfer> transfers = jdbcTemplate.query(sql, new TransferRowMapper());

        return transfers;
    }

    @Override
    public List<Transfer> getAllFromTransfers() {
        String sql = "SELECT * FROM transfer JOIN account ON transfer.account_from = account.account_id " +
                "JOIN tenmo_user ON (account.user_id = tenmo_user.user_id) WHERE transfer_type_id = 1;";

        List<Transfer> transfers = jdbcTemplate.query(sql, new TransferRowMapper());

        return transfers;
    }

    @Override
    public Transfer getTransferByTransferId(Integer id) {
        String sql = "SELECT * FROM transfer JOIN  account ON transfer.account_from = account.account_id " +
                "JOIN tenmo_user ON (account.user_id = tenmo_user.user_id) WHERE transfer_id = ?";

        List<Transfer> transfer = jdbcTemplate.query(sql, new TransferRowMapper(), id);
        if (transfer.size() > 0 ) {
            return transfer.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public Transfer create(Transfer newTransfer) {
        String sql = "INSERT INTO transfer(transfer_type_id, " +
                "transfer_status_id, account_from, account_to, amount) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING transfer_id";

        Integer newTransferId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getTransferTypeId(),
                newTransfer.getTransferStatusId(), newTransfer.getAccountIdFrom(), newTransfer.getAccountIdTo(),
                newTransfer.getAmount());

        return getTransferByTransferId(newTransferId);
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
            transfer.setAmount(resultSet.getBigDecimal("amount"));
            transfer.setUsername(resultSet.getString("username"));
            return transfer;
        }
    }
}
