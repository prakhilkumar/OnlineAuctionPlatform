package com.project.onlineauction.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.onlineauction.entity.BidItem;
import com.project.onlineauction.entity.User;
import com.project.onlineauction.entity.UserBid;

@Repository
public interface UserBidRepository extends JpaRepository<UserBid, Long>{

	List<UserBid> findByAuctionUser(User userId);
	
	UserBid findByAuctionUserAndBidItem(User userId,BidItem bidItem);
	
}
