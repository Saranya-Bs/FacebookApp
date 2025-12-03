package com.facebookapp.dto.FriendRequestDTO;

import java.time.LocalDateTime;

import com.facebookapp.entities.RequestStatus;

public class FriendRequestResponse {
    public Long id;
    public Long senderId;
    public Long receiverId;
    public RequestStatus requestStatus;
    public LocalDateTime createdAt;
}
