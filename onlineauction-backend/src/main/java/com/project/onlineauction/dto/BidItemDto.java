package com.project.onlineauction.dto;

import java.time.LocalDateTime;
import java.util.Comparator;

import com.project.onlineauction.entity.BidItem;
import com.project.onlineauction.entity.UserBid;

public class BidItemDto {

    private Long id;

    private String title;
    private String description;
    private double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int duration;
    private String status;
    private long userId;
    private Integer priority;
    private Double maxBidAmount;
    private String imageName;
    private String imageType;
    private byte[] imageData;
    

	public BidItemDto() {
    	
    }
    
    public BidItemDto(BidItem bidItem) {
    	this.title = bidItem.getTitle();
    	this.description = bidItem.getDescription();
    	this.price = bidItem.getPrice();
    	this.startDate = bidItem.getStartDate();
        this.endDate=bidItem.getEndDate();
    	this.duration = bidItem.getDuration();
    	this.id = bidItem.getId();
    	this.priority = bidItem.getUser().getUserMemberships().size();
    	UserBid userBid = bidItem.getUserBids().stream().max(Comparator.comparingDouble(UserBid :: getAuctionAmount)).orElse(null);
    	if(userBid!=null) {
    		this.maxBidAmount = userBid.getAuctionAmount();
    	}
        this.status=bidItem.getStatus();
        this.imageData= bidItem.getImageData();
        this.imageName= bidItem.getImageName();
        this.imageType= bidItem.getImageType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Double getMaxBidAmount() {
		return maxBidAmount;
	}

	public void setMaxBidAmount(Double maxBidAmount) {
		this.maxBidAmount = maxBidAmount;
	}

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
