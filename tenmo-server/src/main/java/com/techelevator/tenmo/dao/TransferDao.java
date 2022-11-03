package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;


public interface TransferDao {
    List<Transfer> getTransfersByUserId(int id);

    Transfer getTransferByTransferId(int id);



}
