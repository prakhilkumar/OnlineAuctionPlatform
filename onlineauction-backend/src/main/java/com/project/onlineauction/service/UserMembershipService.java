package com.project.onlineauction.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.onlineauction.entity.UserWallet;
import com.project.onlineauction.repo.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.onlineauction.dto.UserMembershipDto;
import com.project.onlineauction.entity.Membership;
import com.project.onlineauction.entity.User;
import com.project.onlineauction.entity.UserMembership;
import com.project.onlineauction.exceptions.AuctionException;
import com.project.onlineauction.repo.MembershipRepository;
import com.project.onlineauction.repo.UserMembershipRepository;
import com.project.onlineauction.repo.Usersrepo;

@Service
public class UserMembershipService {

	@Autowired
	private UserMembershipRepository userMembershipRepository;
	
	@Autowired
	MembershipRepository membershipRepository;
	
	@Autowired
	Usersrepo usersrepo;
	@Autowired
	UserWalletRepository userWalletRepository;
	@Autowired
	UserSession userSession;
	
	public UserMembershipDto upgradeMembership(Long membershipId) {
		
		//check for the validation if the user has the sufficient amount in his wallet
		// Null_pointer and other validation could also be performed
		Membership membership = membershipRepository.findById(membershipId).orElse(null);
		User user = usersrepo.findById(userSession.getLoggedInUser().getId()).orElse(null);
		if(user.getUserWallet()==null || user.getUserWallet().getTotalAmount()<membership.getPrice()) {
			throw new AuctionException("You Do not have Sufficient balance","400");
		}
		UserWallet userwallet=user.getUserWallet();
		userwallet.setTotalAmount(userwallet.getTotalAmount()-membership.getPrice());
		userWalletRepository.save(userwallet);

		// check whether user already have the same upgrade plan		
	    // If user choose the gold after the silver then end the silver first then create for gold so that we have always one membership available to the user
		UserMembership userMembership = new UserMembership();
		userMembership.setMembership(membership);
		userMembership.setPrice(membership.getPrice());
		userMembership.setUser(user);
		userMembership.setStartDate(LocalDateTime.now());
		userMembership.setEndDate(LocalDateTime.now().plusMonths(3));
		userMembershipRepository.save(userMembership);
		return new UserMembershipDto(userMembership);
	}
	
	public List<UserMembershipDto> findUserMembershipsByUserId() {
		User user = userSession.getLoggedInUser();
		user = usersrepo.findById(user.getId()).orElse(null);
		List<UserMembershipDto> userMembershipDtos = new ArrayList<>();
		List<UserMembership> userMemberships =  user.getUserMemberships();
		for(UserMembership userMembership : userMemberships) {
			if(userMembership.getEndDate().compareTo(LocalDateTime.now())<0)
				continue;
			UserMembershipDto userMembershipDto = new UserMembershipDto(userMembership);
			userMembershipDtos.add(userMembershipDto);
		}
		return userMembershipDtos;
	}
}
