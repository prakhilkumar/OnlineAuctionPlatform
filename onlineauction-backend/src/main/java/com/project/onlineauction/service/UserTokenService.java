package com.project.onlineauction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.onlineauction.dto.TokenResponse;
import com.project.onlineauction.util.JwtTokenUtil;

@Service
public class UserTokenService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	public TokenResponse createToken(String userName) {
		return jwtTokenUtil.generateToken(userName);
	}
	
	public String getUserNameFromToken(String token) {
		if (token != null && token.startsWith("Bearer ")) {
			token = token.substring(7);
		}
		if(jwtTokenUtil.isTokenExpired(token))
			throw new RuntimeException("Token is expired");
		return jwtTokenUtil.getUsernameFromToken(token);
	}

}
