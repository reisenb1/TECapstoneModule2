package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.services.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
@CrossOrigin
public class TransferController {

    private final TransferDao transferDao;
    private final TransferService transferService;


    public TransferController(TransferDao transferDao, TransferService transferService) {
        this.transferDao = transferDao;
        this.transferService = transferService;
    }

    @GetMapping("/transfersTo")
    public List<Transfer> getAllToTransfers() {
        return transferService.getAllToTransfers();
    }

    @GetMapping("/transfersFrom")
    public List<Transfer> getAllFromTransfers() {
        return transferService.getAllFromTransfers();
    }

    @GetMapping("/transfers/{id}")
    public Transfer getTransferByTransferId(@PathVariable Integer id) {
        return transferService.getTransferByTransferId(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfers", method = RequestMethod.POST)
    public Transfer createTransfer(@RequestBody Transfer newTransfer) {
        return transferService.create(newTransfer);
    }

}
