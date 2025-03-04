package com.project.onlineauction.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.onlineauction.dto.TokenResponse;
import com.project.onlineauction.dto.UserDto;
import com.project.onlineauction.entity.User;
import com.project.onlineauction.entity.UserWallet;
import com.project.onlineauction.exceptions.AuctionException;
import com.project.onlineauction.repo.UserWalletRepository;
import com.project.onlineauction.repo.Usersrepo;

import Resquest.LoginRequest;

@Service
public class Userservice {

	@Autowired
	private Usersrepo usersrepo;
	@Autowired
	private UserWalletRepository userWalletRepository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserTokenService userTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
	@Autowired
	private UserSession userSession;
	public User addUser(UserDto userDto) {
		User userDB =  usersrepo.findByEmail(userDto.getEmail());
		if(userDB!=null) {
			throw new AuctionException("User already exists", "400");
		}

		UserWallet userwallet=new UserWallet();
		userwallet.setTotalAmount(2000.0);
		userwallet.setUser(userDB);
		userwallet.setTotalLienAmount(0.0);
		userWalletRepository.save(userwallet);
		userDB = new User();
		BeanUtils.copyProperties(userDto, userDB);
		userDB.setUserWallet(userwallet);
		userDB.setPassword(passwordEncoder.encode(userDto.getPassword()));
		return usersrepo.save(userDB);
	}


	public TokenResponse loginUser(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return userTokenService.createToken(loginRequest.getUserId());
		
	}

	public String getUserName() {
		System.out.print("*************************************************************"+userSession.getLoggedInUser().getName());
		return userSession.getLoggedInUser().getName();
	}
}
