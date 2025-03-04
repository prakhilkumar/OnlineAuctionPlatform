package com.project.onlineauction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.onlineauction.dto.UserMembershipDto;
import com.project.onlineauction.service.UserMembershipService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auction")
public class UserMembershipController {

	@Autowired
	private UserMembershipService userMembershipService;
	
	@PostMapping("/user/membership/upgrade")
	public ResponseEntity<UserMembershipDto> upgradeMembership(@RequestParam("membershipId") Long membershipId) {
		UserMembershipDto userMembershipDto = userMembershipService.upgradeMembership(membershipId);
		return new ResponseEntity<>(userMembershipDto, HttpStatus.CREATED);
	}


	@GetMapping("/user/memberships")
	public List<UserMembershipDto> getUserMembershipByUserId() {
		return userMembershipService.findUserMembershipsByUserId();
	}
	
}
