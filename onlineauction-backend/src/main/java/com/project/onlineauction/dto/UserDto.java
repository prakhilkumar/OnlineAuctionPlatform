package com.project.onlineauction.dto;

import java.util.List;

public class UserDto {

    private String name;
    private long id;
    private String email;
    private String password;
    private String mobilenumber;
    private List<BidItemDto> bidItems;
    private List<UserBidDto> userBids; 
   

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

	public List<BidItemDto> getBidItems() {
		return bidItems;
	}

	public void setBidItems(List<BidItemDto> bidItems) {
		this.bidItems = bidItems;
	}

	public List<UserBidDto> getUserBids() {
		return userBids;
	}

	public void setUserBids(List<UserBidDto> userBids) {
		this.userBids = userBids;
	}

}
