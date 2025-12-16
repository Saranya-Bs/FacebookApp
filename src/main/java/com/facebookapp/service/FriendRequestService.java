package com.facebookapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.facebookapp.dto.ApiResponseDTO;
import com.facebookapp.dto.FriendRequestDTO.FriendRequestResponse;
import com.facebookapp.dto.FriendRequestDTO.SendFriendRequest;
import com.facebookapp.entities.FriendRequest;
import com.facebookapp.entities.NotificationType;
import com.facebookapp.entities.RequestStatus;
import com.facebookapp.entities.User;
import com.facebookapp.exception.FriendRequestException;
import com.facebookapp.exception.UnSupportedOperationException;
import com.facebookapp.exception.UserNotFoundException;
import com.facebookapp.repository.FriendRequestRepository;
import com.facebookapp.repository.UserRepository;
import com.facebookapp.security.SecurityUtil;

@Service
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public FriendRequestService(FriendRequestRepository friendRequestRepository,UserRepository userRepository,NotificationService notificationService){
        this.friendRequestRepository=friendRequestRepository;
        this.userRepository=userRepository;
        this.notificationService=notificationService;
    }
    
    public ResponseEntity<ApiResponseDTO> sendFriendRequest(SendFriendRequest FriendRequest){


        User currentUser=userRepository.findByEmail(SecurityUtil.getLoggedInUserEmail()).orElseThrow(()-> new UserNotFoundException("User not found with email: "+SecurityUtil.getLoggedInUserEmail()));

        User receiver=userRepository.findById(FriendRequest.receiverId).orElseThrow(()->new UserNotFoundException("User not found with id: "+FriendRequest.receiverId));

        if(receiver.getId()==currentUser.getId()){
            throw new UnSupportedOperationException("Request to self is not supported");
        }

        if(
            friendRequestRepository.findBySenderIdAndReceiverId(currentUser, receiver)
            .filter(fr->fr.getRequestStatus()==RequestStatus.ACCEPTED)
            .isPresent()
            ||
            friendRequestRepository.findBySenderIdAndReceiverId(receiver,currentUser)
            .filter(fr->fr.getRequestStatus()==RequestStatus.ACCEPTED)
            .isPresent()
        ){
            throw new UnSupportedOperationException("Request to existing friend is not supported");
        }

        if(friendRequestRepository.findBySenderIdAndReceiverId(currentUser,receiver)
            .filter(fr->fr.getRequestStatus()==RequestStatus.PENDING).isPresent()){
                throw new FriendRequestException("Request to the user already exists");
        }

        FriendRequest friendRequest=new FriendRequest();
        friendRequest.setSenderId(currentUser);
        friendRequest.setReceiverId(receiver);
        friendRequest.setRequestStatus(RequestStatus.PENDING);
        friendRequest.setCreatedAt(LocalDateTime.now());
        friendRequest.setUpdatedAt(LocalDateTime.now());

        friendRequestRepository.save(friendRequest);

        notificationService.createNotification(currentUser, receiver, currentUser.getFirstName()+"sent you a friend request", NotificationType.FRIEND_REQUEST);

        return ResponseEntity.ok(new ApiResponseDTO(
            true,
            "Friend request sent successfully",
            LocalDateTime.now()
        ));
    }

    public List<FriendRequestResponse> getPendingRequests(){
        User currentUser=userRepository.findByEmail(SecurityUtil.getLoggedInUserEmail()).orElseThrow(()-> new UserNotFoundException("User not found with email: "+SecurityUtil.getLoggedInUserEmail()));

        return friendRequestRepository
                .findByReceiverIdAndRequestStatus(currentUser,RequestStatus.PENDING)
                .stream()
                .map( req-> {
                    FriendRequestResponse response=new FriendRequestResponse();
                    response.id=req.getId();
                    response.senderId=req.getSenderId().getId();
                    response.receiverId=req.getReceiverId().getId();
                    response.requestStatus=req.getRequestStatus();
                    response.createdAt=req.getCreatedAt();

                    return response;
                })
                .toList();
    }

    public ResponseEntity<ApiResponseDTO> acceptRequest(Long requestId){
        User currentUser=userRepository.findByEmail(SecurityUtil.getLoggedInUserEmail()).orElseThrow(()-> new UserNotFoundException("User not found with email: "+SecurityUtil.getLoggedInUserEmail()));

        FriendRequest request=friendRequestRepository.findById(requestId).orElseThrow(()->new FriendRequestException("Request Not Found!"));

        request.setRequestStatus(RequestStatus.ACCEPTED);
        request.setUpdatedAt(LocalDateTime.now());

        friendRequestRepository.save(request);

        notificationService.createNotification(currentUser, request.getSenderId(), currentUser.getFirstName()+"accepted your friend request", NotificationType.FRIEND_REQUEST_ACCEPTED);

        return ResponseEntity.ok(new ApiResponseDTO(true,"Request Accepted",LocalDateTime.now()));
    }

    public ResponseEntity<ApiResponseDTO> declineRequest(Long requestId){
        User currentUser=userRepository.findByEmail(SecurityUtil.getLoggedInUserEmail()).orElseThrow(()-> new UserNotFoundException("User not found with email: "+SecurityUtil.getLoggedInUserEmail()));

        FriendRequest request=friendRequestRepository.findById(requestId).orElseThrow(()->new FriendRequestException("Request Not Found!"));

        request.setRequestStatus(RequestStatus.DECLINED);
        request.setUpdatedAt(LocalDateTime.now());

        friendRequestRepository.save(request);

        notificationService.createNotification(currentUser, request.getSenderId(), currentUser.getFirstName()+"declined your friend request", NotificationType.FRIEND_REQUEST_ACCEPTED);

        return ResponseEntity.ok(new ApiResponseDTO(true,"Request Declined",LocalDateTime.now()));
    }
}
