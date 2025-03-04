package com.project.onlineauction.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserWalletHistory {

    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne
    private UserWallet userWallet;
    
    private Double amount;
    private String transactionType;
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public UserWallet getUserWallet() {
		return userWallet;
	}
	public void setUserWallet(UserWallet userWallet) {
		this.userWallet = userWallet;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
    
    
}
