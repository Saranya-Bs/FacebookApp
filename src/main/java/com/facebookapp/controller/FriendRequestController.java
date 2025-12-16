package com.facebookapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebookapp.dto.ApiResponseDTO;
import com.facebookapp.dto.FriendRequestDTO.FriendRequestResponse;
import com.facebookapp.dto.FriendRequestDTO.SendFriendRequest;
import com.facebookapp.service.FriendRequestService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/friends")
public class FriendRequestController {
    
    private final FriendRequestService friendRequestService;

    public FriendRequestController(FriendRequestService friendRequestService){
        this.friendRequestService=friendRequestService;
    }

    @PostMapping("/sendRequest")
    public ResponseEntity<ApiResponseDTO> sendFriendRequest(@RequestBody SendFriendRequest request) {
        return friendRequestService.sendFriendRequest(request);
    }

    @GetMapping("/pendingRequests")
    public List<FriendRequestResponse> getPendingRequests() {
        return friendRequestService.getPendingRequests();
    }
    
    @PatchMapping("/acceptRequest/{requestId}")
    public ResponseEntity<ApiResponseDTO> acceptRequest(@PathVariable Long requestId){
        return friendRequestService.acceptRequest(requestId);
    }
    
    @PatchMapping("/declineRequest/{requestId}")
    public ResponseEntity<ApiResponseDTO> declineRequest(@PathVariable Long requestId){
        return friendRequestService.declineRequest(requestId);
    }
}
