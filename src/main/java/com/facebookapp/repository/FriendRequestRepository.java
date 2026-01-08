package com.facebookapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebookapp.dto.FriendRequestDTO.FriendsDTO;
import com.facebookapp.entities.FriendRequest;
import com.facebookapp.entities.RequestStatus;
import com.facebookapp.entities.User;
import java.util.List;


public interface FriendRequestRepository extends JpaRepository<FriendRequest,Long>{
    Optional<FriendRequest> findBySenderIdAndReceiverId(User sender,User receiver);

    List<FriendRequest> findByReceiverIdAndRequestStatus(User receiverId,RequestStatus status);

    List<FriendsDTO> findBySenderIdAndRequestStatus(User senderId,RequestStatus status);
}
