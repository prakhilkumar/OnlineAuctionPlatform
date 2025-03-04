package com.project.onlineauction.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class UserBid {

    @Id
    @GeneratedValue
    private Long id;  
    
    @ManyToOne
    private BidItem bidItem;
    @ManyToOne
    private User auctionUser;
    
    private Double auctionAmount;
    private String status;
    
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BidItem getBidItem() {
		return bidItem;
	}
	public void setBidItem(BidItem bidItem) {
		this.bidItem = bidItem;
	}
	public User getAuctionUser() {
		return auctionUser;
	}
	public void setAuctionUser(User auctionUser) {
		this.auctionUser = auctionUser;
	}
	public Double getAuctionAmount() {
		return auctionAmount;
	}
	public void setAuctionAmount(Double auctionAmount) {
		this.auctionAmount = auctionAmount;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
    
    
}
