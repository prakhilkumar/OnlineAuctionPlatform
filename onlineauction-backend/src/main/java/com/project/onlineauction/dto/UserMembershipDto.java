package com.project.onlineauction.dto;

import java.time.LocalDateTime;

import com.project.onlineauction.entity.Membership;
import com.project.onlineauction.entity.User;
import com.project.onlineauction.entity.UserMembership;

public class UserMembershipDto {

    private long id;
    
    private User user;
    
    private Membership membership;
    
    private String type;
    
    private Double price;
    
    private String description;
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public UserMembershipDto() {
    	
    }
    
    public UserMembershipDto(UserMembership userMembership) {
    	this.id = userMembership.getId();
    	this.type = userMembership.getMembership().getType();
    	this.price = userMembership.getPrice();
    	this.description = userMembership.getMembership().getDescription();
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	
}
