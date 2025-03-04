package com.project.onlineauction.dto;

import java.time.LocalDateTime;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.onlineauction.entity.UserBid;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBidDto {

    private Long id;
	private String title;
	private String description;
    
    private BidItemDto bidItem;

    private UserDto auctionUser;
    
    private Double bidAmount;
	private Double maxBidAmount;
    private Double price;
    
    private String status;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Long userId;
    
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
	private LocalDateTime endDate;
    
    public UserBidDto() {
    	
    }
    
    public UserBidDto(UserBid userBid) {
    	this.id = userBid.getId();
        this.title=userBid.getBidItem().getTitle();
		this.description=userBid.getBidItem().getDescription();
    	this.bidAmount = userBid.getAuctionAmount();
    	this.price = userBid.getBidItem().getPrice();
    	this.createdDate = userBid.getCreatedDate();
    	this.updatedDate = userBid.getUpdatedDate();
    	this.status = userBid.getStatus();
		this.endDate=userBid.getBidItem().getEndDate();
		UserBid userBidMax = userBid.getBidItem().getUserBids().stream().max(Comparator.comparingDouble(UserBid :: getAuctionAmount)).orElse(null);
		if(userBidMax!=null) {
			this.maxBidAmount = userBidMax.getAuctionAmount();
		}
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BidItemDto getBidItem() {
		return bidItem;
	}
	public void setBidItem(BidItemDto bidItem) {
		this.bidItem = bidItem;
	}
	public UserDto getAuctionUser() {
		return auctionUser;
	}
	public void setAuctionUser(UserDto auctionUser) {
		this.auctionUser = auctionUser;
	}
	public Double getBidAmount() {
		return bidAmount;
	}
	public void setBidAmount(Double bidAmount) {
		this.bidAmount = bidAmount;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Double getMaxBidAmount() {
		return maxBidAmount;
	}

	public void setMaxBidAmount(Double maxBidAmount) {
		this.maxBidAmount = maxBidAmount;
	}
}
