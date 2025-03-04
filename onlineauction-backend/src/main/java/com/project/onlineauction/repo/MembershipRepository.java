package com.project.onlineauction.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.onlineauction.entity.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

}
