package com.project.onlineauction.service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.onlineauction.dto.BidItemDto;
import com.project.onlineauction.dto.UserBidDto;
import com.project.onlineauction.entity.BidItem;
import com.project.onlineauction.entity.User;
import com.project.onlineauction.entity.UserBid;
import com.project.onlineauction.entity.UserWallet;
import com.project.onlineauction.exceptions.AuctionException;
import com.project.onlineauction.repo.BidRepository;
import com.project.onlineauction.repo.UserBidRepository;
import com.project.onlineauction.repo.UserWalletRepository;
import com.project.onlineauction.repo.Usersrepo;
import org.springframework.web.multipart.MultipartFile;

@Service
public class BidService {

    @Autowired
    private BidRepository bidRepository;
    @Autowired
    private UserBidRepository userBidRepository;
    @Autowired
    private Usersrepo userrepo;
    @Autowired
    private UserWalletRepository userWalletRepository;
    @Autowired
    private UserSession userSession;

    public List<BidItemDto> getAllBids(String tabType) {
    	User user = userSession.getLoggedInUser();
    	List<BidItemDto> bidItemDtos = new ArrayList<>();
        List<BidItem> bidItems = bidRepository.findAll();
        bidItems.stream().forEach(bidItem -> {
        
        	if(tabType!=null && tabType.equals("CREATEDBID") && !user.getId().equals(bidItem.getUser().getId())) {
        		return;
        	}
        	if(tabType!=null && tabType.equals("AVAILBID") && user.getId().equals(bidItem.getUser().getId())) {
        		return;
        	}
        	BidItemDto bidItemDto = new BidItemDto(bidItem);
        	
        	bidItemDtos.add(bidItemDto);
        });
        return bidItemDtos.stream().sorted(Comparator.comparingInt(BidItemDto::getPriority).reversed()).collect(Collectors.toList());
    }
    
    public List<UserBidDto> getUserBidsByUserId() {
    	User user = userSession.getLoggedInUser();
    	List<UserBidDto> userBidDtos = new ArrayList<>();
    	List<UserBid> userBids =  userBidRepository.findByAuctionUser(userrepo.findById(user.getId()).get());
    	userBids.stream().forEach(userBid -> {
    		UserBidDto userBidDto = new UserBidDto(userBid);
    		userBidDtos.add(userBidDto);
    	});
    	return userBidDtos;
    }

    public BidItemDto getBidById(Long id) {
    	BidItem bidItem = bidRepository.findById(id).orElse(null);
        return new BidItemDto(bidItem);
    }
    
    public BidItemDto create(BidItemDto bidDto,MultipartFile imageFile) {
    	
    	User user = userrepo.findById(userSession.getLoggedInUser().getId()).orElse(null);
    	if(user.getBidItems().size() >=5 && user.getUserMemberships().size()==0 ) {
    		throw new AuctionException("Please Upgrade your plan! You need to be a premium member to create more bids", "400");
    	}
    	BidItem bidItem = new BidItem();
    	bidItem.setDescription(bidDto.getDescription());
    	bidItem.setDuration(bidDto.getDuration());
    	bidItem.setPrice(bidDto.getPrice());
    	bidItem.setStartDate(bidDto.getStartDate());
		bidItem.setEndDate(bidDto.getEndDate());
    	bidItem.setStatus("AVAILABLE");
    	bidItem.setTitle(bidDto.getTitle());
    	bidItem.setUser(user);
		bidItem.setImageName(bidDto.getImageName());
		bidItem.setImageType(bidDto.getImageType());
		bidItem.setImageData(bidDto.getImageData());
    	bidRepository.save(bidItem);
    	return bidDto;
    	
    }


    public com.project.onlineauction.dto.BidItemDto createBid(BidItemDto bidDto, MultipartFile imageFile) throws IOException {
		bidDto.setImageName(imageFile.getOriginalFilename());
		bidDto.setImageData(imageFile.getBytes());
		bidDto.setImageType(imageFile.getContentType());
        return create(bidDto,imageFile);
    }

    public BidItemDto placeBid(Long id, UserBidDto userBidDto) {
    	User user = userrepo.findById(userSession.getLoggedInUser().getId()).orElse(null);
    	UserWallet userWallet = user.getUserWallet();
    	
    	if(userWallet==null || userWallet.getTotalAmount()<userBidDto.getBidAmount() + userWallet.getTotalLienAmount()) {
    		throw new AuctionException("You do not have the sufficient balance to place this Bid", "400");
    	}
    	
        BidItem bidItem = bidRepository.findById(id).orElse(null);
        if (bidItem != null) {   
        	UserBid maxUserBid = bidItem.getUserBids().stream().max(Comparator.comparingDouble(UserBid :: getAuctionAmount)).orElse(null);
        	if(bidItem.getPrice()>=userBidDto.getBidAmount() ) {
        		throw new AuctionException("Bid must be higher than offered amount","400");
        	}
        	if((maxUserBid!=null && userBidDto.getBidAmount() <= maxUserBid.getAuctionAmount())) {
        		throw new AuctionException("Current Bid "+maxUserBid.getAuctionAmount()+"! New Bid should be higher than Current Bid", null);
        	}
        	
        	userWallet.setTotalLienAmount(userWallet.getTotalLienAmount() + userBidDto.getBidAmount());
        	userWalletRepository.save(userWallet);
        	UserBid userBid = userBidRepository.findByAuctionUserAndBidItem(user,bidItem);
        	if(userBid == null) {
        		userBid = new UserBid();
        	}
        	userBid.setAuctionAmount(userBidDto.getBidAmount());
        	userBid.setAuctionUser(user);
        	userBid.setBidItem(bidItem);
        	userBid.setCreatedDate(LocalDateTime.now());
        	userBid.setUpdatedDate(LocalDateTime.now());
        	userBid.setStatus("BID_PLACED");
        	userBidRepository.save(userBid);
            return new BidItemDto(bidItem);
        }
        return null;
    }
    
    public void sellBidItem(Long bidItemId) {
    	BidItem bidItem =  bidRepository.findById(bidItemId).orElse(null);
    	
    	if(!"AVAILABLE".equalsIgnoreCase(bidItem.getStatus())) {
    		throw new AuctionException("Auction already has been done on this item", "400");
    	}

    	
    	if(bidItem.getUserBids().size() <=0 ) {
			bidItem.setStatus("UNSOLD");
			bidRepository.save(bidItem);
    		throw new AuctionException("No Bid Available for your Item", "400");
    	}
    	UserBid maxUserBid = bidItem.getUserBids().stream().max(Comparator.comparingDouble(UserBid :: getAuctionAmount)).orElse(null);
    	boolean isSold = false;
    	if(maxUserBid != null ) {
    		UserWallet userWallet = maxUserBid.getAuctionUser().getUserWallet();
    		userWallet.setTotalAmount(userWallet.getTotalAmount() - maxUserBid.getAuctionAmount());
    		userWallet.setTotalLienAmount(userWallet.getTotalLienAmount() - maxUserBid.getAuctionAmount());
    		userWalletRepository.save(userWallet);
    		maxUserBid.setStatus("DEAL");
    		User user = userrepo.findById(userSession.getLoggedInUser().getId()).orElse(null);
    		UserWallet publisherUserWallet =  user.getUserWallet();
    		if(publisherUserWallet!=null ) {
    			publisherUserWallet.setTotalAmount(publisherUserWallet.getTotalAmount() + maxUserBid.getAuctionAmount());
    			userWalletRepository.save(publisherUserWallet);
    		}
    		bidItem.setStatus("SOLD");
    		bidRepository.save(bidItem);
    		isSold = true;
    	}
        for(UserBid userBid : bidItem.getUserBids()) {
        	if(maxUserBid!=null && maxUserBid.getId().equals(userBid.getId())) {
        		continue;
        	}
        	User auctionUser = userBid.getAuctionUser();
        	UserWallet userWallet = auctionUser.getUserWallet();
        	userWallet.setTotalLienAmount(userWallet.getTotalLienAmount() - userBid.getAuctionAmount());
        	userWalletRepository.save(userWallet);
        	userBid.setStatus("NO_DEAL");
        	userBidRepository.save(userBid);
        }
        if(!isSold) {
        	bidItem.setStatus("UNSOLD");
        	bidRepository.save(bidItem);
        }
    }
}

