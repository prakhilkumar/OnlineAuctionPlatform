package com.project.onlineauction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.onlineauction.entity.User;
import com.project.onlineauction.repo.Usersrepo;

@Service
public class UserDetailService implements UserDetailsService{

	@Autowired
	private Usersrepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final User user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        UserDetails userDetails = new UserDetails(user);
        return userDetails;
	}
	
	
}
