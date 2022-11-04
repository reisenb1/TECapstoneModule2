package com.techelevator.dao;

import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

public class JdbcTransferDaoTests extends BaseDaoTests{

    private static final Transfer TRANSFER_1 = new Transfer(1001, 2, 2, 2010, 2011, new BigDecimal("500.00"), "user1", "Approved", "Send");
    private static final Transfer TRANSFER_2 = new Transfer(1002, 1, 2, 2010, 2011, new BigDecimal("400.00"), "user2", "Approved", "Request");

    private JdbcTransferDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
    }

    @Test
    public void getTransferByTransferId_returns_correct_transfer() {
        Transfer actualTransfer = sut.getTransferByTransferId(TRANSFER_1.getTransferId());

        assertTransfersMatch(TRANSFER_1, actualTransfer);
    }

    private void assertTransfersMatch(Transfer expected, Transfer actual) {
        Assert.assertEquals(expected.getTransferId(), actual.getTransferId());
        Assert.assertEquals(expected.getTransferTypeId(), actual.getTransferTypeId());
        Assert.assertEquals(expected.getTransferStatusId(), actual.getTransferStatusId());
        Assert.assertEquals(expected.getAccountIdFrom(), actual.getAccountIdFrom());
        Assert.assertEquals(expected.getAccountIdTo(), actual.getAccountIdTo());
        Assert.assertEquals(expected.getAmount(), actual.getAmount());
    }

}
