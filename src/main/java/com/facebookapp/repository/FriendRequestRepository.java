package com.facebookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebookapp.entities.FriendRequest;

public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long>{
    
}
