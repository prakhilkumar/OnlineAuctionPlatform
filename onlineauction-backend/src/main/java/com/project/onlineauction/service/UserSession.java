package com.project.onlineauction.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.project.onlineauction.entity.User;

@Component
public class UserSession {
	
	public User getLoggedInUser() {
		Object object =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(object instanceof UserDetails) {
			UserDetails customerDetails = (UserDetails) object;
			return customerDetails.getUser();
		}
		return null;
	}

}
