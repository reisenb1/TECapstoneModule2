package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;


public interface TransferDao {
    List<Transfer> getAllToTransfers();

    List<Transfer> getAllFromTransfers();

    Transfer getTransferByTransferId(Integer id);

    Transfer create(Transfer newTransfer);



}
