package com.project.onlineauction.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.onlineauction.entity.UserMembership;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {

}
