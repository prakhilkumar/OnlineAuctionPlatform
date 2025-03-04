package com.project.onlineauction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.onlineauction.dto.MembershipDto;
import com.project.onlineauction.service.MembershipService;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/auction")
public class MembershipController {
	
	@Autowired
	private MembershipService membershipService;
	
    @GetMapping("/memberships")
    public List<MembershipDto> getAllBids() {

        return membershipService.getAllMemberShips();
    }

}
