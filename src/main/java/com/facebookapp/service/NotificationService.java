package com.facebookapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.facebookapp.dto.NotificationDTO;
import com.facebookapp.entities.NotificationType;
import com.facebookapp.entities.Notifications;
import com.facebookapp.entities.User;
import com.facebookapp.exception.NotificationNotFoundException;
import com.facebookapp.exception.UnSupportedOperationException;
import com.facebookapp.exception.UserNotFoundException;
import com.facebookapp.repository.NotificationRepository;
import com.facebookapp.repository.UserRepository;
import com.facebookapp.security.SecurityUtil;

@Service
public class NotificationService {
    
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,UserRepository userRepository){
        this.notificationRepository=notificationRepository;
        this.userRepository=userRepository;
    }

    public void createNotification(User sender,User receiver,String message,NotificationType type){
        Notifications notification=new Notifications();
        notification.setSenderId(sender);
        notification.setReceiverId(receiver);
        notification.setNotificationType(type);
        notification.setNotification(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setIsRead(false);
        notification.setUpdatedAt(LocalDateTime.now());

        notificationRepository.save(notification);
    }

    public List<NotificationDTO> getAllNotifications(){
        User currentUser=userRepository.findByEmail(SecurityUtil.getLoggedInUserEmail()).orElseThrow(()-> new UserNotFoundException("User not found with email: "+SecurityUtil.getLoggedInUserEmail()));

        List<Notifications> list=notificationRepository.findAllByReceiverId(currentUser);

        List<NotificationDTO> result=list.stream()
                                        .map(ele->{
                                            NotificationDTO notificationDTO=new NotificationDTO();
                                            notificationDTO.setSenderId(ele.getSenderId().getId());
                                            notificationDTO.setReceiverId(ele.getReceiverId().getId());
                                            notificationDTO.setIsRead(ele.getIsRead());
                                            notificationDTO.setId(ele.getId());
                                            notificationDTO.setNotification(ele.getNotification());
                                            notificationDTO.setNotificationType(ele.getNotificationType());
                                            return notificationDTO;
                                        })
                                        .toList();
        return result;
    }

    public List<NotificationDTO> getUnReadNotifications(){
        User currentUser=userRepository.findByEmail(SecurityUtil.getLoggedInUserEmail()).orElseThrow(()-> new UserNotFoundException("User not found with email: "+SecurityUtil.getLoggedInUserEmail()));
        List<Notifications> list=notificationRepository.findByReceiverIdAndIsReadFalse(currentUser);

        List<NotificationDTO> result=list.stream()
                                        .map(ele->{
                                            NotificationDTO notificationDTO=new NotificationDTO();
                                            notificationDTO.setSenderId(ele.getSenderId().getId());
                                            notificationDTO.setReceiverId(ele.getReceiverId().getId());
                                            notificationDTO.setIsRead(ele.getIsRead());
                                            notificationDTO.setId(ele.getId());
                                            notificationDTO.setNotification(ele.getNotification());
                                            notificationDTO.setNotificationType(ele.getNotificationType());
                                            return notificationDTO;
                                        })
                                        .toList();
        return result;
    }

    public void markAsRead(Long notificationId){
        User currentUser=userRepository.findByEmail(SecurityUtil.getLoggedInUserEmail()).orElseThrow(()-> new UserNotFoundException("User not found with email: "+SecurityUtil.getLoggedInUserEmail()));
        Notifications notification =notificationRepository.findById(notificationId).orElseThrow(()->new NotificationNotFoundException("Notification not Found"));



        if(notification.getReceiverId().getId()!=currentUser.getId()){
            throw new UnSupportedOperationException("You do not have access to this notification");
        }

        notification.setIsRead(true);
        notificationRepository.save(notification);
    } 

}
