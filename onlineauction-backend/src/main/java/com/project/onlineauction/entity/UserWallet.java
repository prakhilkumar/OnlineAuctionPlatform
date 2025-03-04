package com.project.onlineauction.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class UserWallet {

    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne
    private User user;
    
    @OneToMany(mappedBy = "userWallet")
    private List<UserWalletHistory> userWallethistory;
    
    private Double totalAmount;
    private Double totalLienAmount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<UserWalletHistory> getUserWallethistory() {
		return userWallethistory;
	}

	public void setUserWallethistory(List<UserWalletHistory> userWallethistory) {
		this.userWallethistory = userWallethistory;
	}

	public Double getTotalLienAmount() {
		return totalLienAmount;
	}

	public void setTotalLienAmount(Double totalLienAmount) {
		this.totalLienAmount = totalLienAmount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
    
}
