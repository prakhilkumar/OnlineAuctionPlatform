package com.project.onlineauction.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {

    private String name;
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String mobilenumber;
    @OneToMany(mappedBy = "user")
    private List<BidItem> bidItems;
	@OneToMany(mappedBy = "auctionUser")
    private List<UserBid> userBids; 
	@OneToOne
	private UserWallet userWallet;
	
	@OneToMany(mappedBy = "user")
	private List<UserMembership> userMemberships;
    
    public List<BidItem> getBidItems() {
		return bidItems;
	}

	public void setBidItems(List<BidItem> bidItems) {
		this.bidItems = bidItems;
	}

	public List<UserBid> getUserBids() {
		return userBids;
	}

	public void setUserBids(List<UserBid> userBids) {
		this.userBids = userBids;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<UserMembership> getUserMemberships() {
		return userMemberships;
	}

	public void setUserMemberships(List<UserMembership> userMemberships) {
		this.userMemberships = userMemberships;
	}

	public UserWallet getUserWallet() {
		return userWallet;
	}

	public void setUserWallet(UserWallet userWallet) {
		this.userWallet = userWallet;
	}
    
    

}
