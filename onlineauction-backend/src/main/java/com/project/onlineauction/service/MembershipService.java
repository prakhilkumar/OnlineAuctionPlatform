package com.project.onlineauction.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.onlineauction.dto.MembershipDto;
import com.project.onlineauction.entity.Membership;
import com.project.onlineauction.repo.MembershipRepository;

@Service
public class MembershipService {

	@Autowired
	private MembershipRepository membershipRepository;
	
	public List<MembershipDto> getAllMemberShips() {
		List<MembershipDto> membershipDtos = new ArrayList<>();
		List<Membership> memberships =  membershipRepository.findAll();
		memberships.stream().forEach(membership -> {
			MembershipDto membershipDto = new MembershipDto();
			BeanUtils.copyProperties(membership, membershipDto);
			membershipDtos.add(membershipDto);
		});
		return membershipDtos;
	}
}
