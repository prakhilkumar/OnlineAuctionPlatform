package com.project.onlineauction.controller;

import com.project.onlineauction.entity.UserWallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.onlineauction.service.WalletService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @GetMapping("wallet/amount")
    public ResponseEntity<UserWallet> getWallet(){
        return new ResponseEntity<>(walletService.getWallet(), HttpStatus.OK);
    }

}
