package com.project.onlineauction.repo;

import com.project.onlineauction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Usersrepo extends JpaRepository<User,Long> {
	
    User findByEmail(String email);

}
