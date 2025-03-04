package com.project.onlineauction.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class BidItem {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int duration;
    private String status;
    private String imageName;
    private String imageType;
    private byte[] imageData;
    
    @ManyToOne
    private User user;
    
    @OneToMany(mappedBy = "bidItem")
    private List<UserBid> userBids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

	public List<UserBid> getUserBids() {
		return userBids;
	}

	public void setUserBids(List<UserBid> userBids) {
		this.userBids = userBids;
	}

    public LocalDateTime getEndDate() {
        return endDate;
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

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}


