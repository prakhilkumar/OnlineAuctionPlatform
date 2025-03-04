package com.project.onlineauction.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.onlineauction.entity.BidItem;

@Repository
public interface BidRepository extends JpaRepository<BidItem, Long> {

}

