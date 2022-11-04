package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {
    private final TransferDao transferDao;

    public TransferService(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    public List<Transfer> getAllTransfers() {
        return transferDao.getAllTransfers();
    }

    public Transfer getTransferByTransferId(Integer id) {
        return transferDao.getTransferByTransferId(id);
    }

    public Transfer create(Transfer newTransfer) {
        return transferDao.create(newTransfer);
    }
}
