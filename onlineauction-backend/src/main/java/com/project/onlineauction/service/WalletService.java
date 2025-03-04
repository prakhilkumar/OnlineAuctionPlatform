package com.project.onlineauction.service;

import com.project.onlineauction.entity.User;
import com.project.onlineauction.entity.UserWallet;
import com.project.onlineauction.exceptions.AuctionException;
import com.project.onlineauction.repo.Usersrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    private Usersrepo userrepo;
    @Autowired
    private UserSession userSession;
    public UserWallet getWallet(){
        User user= userrepo.findById(userSession.getLoggedInUser().getId()).orElse(null);
        if(user==null)
            throw new AuctionException("user not exist","400");

        if(user.getUserWallet()==null)
            throw new AuctionException("user wallet not exist","400");
        return user.getUserWallet();
    }

}
