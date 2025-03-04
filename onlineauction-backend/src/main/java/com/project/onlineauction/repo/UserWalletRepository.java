package com.project.onlineauction.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.onlineauction.entity.UserWallet;
@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet,Long>{

}
