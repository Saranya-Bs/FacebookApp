package com.facebookapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.facebookapp.dto.ApiResponseDTO;
import com.facebookapp.dto.NotificationDTO;
import com.facebookapp.service.NotificationService;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api")
public class NotificationsController {
    
    private final NotificationService notificationService;

    public NotificationsController(NotificationService notificationService){
        this.notificationService=notificationService;
    }

    @GetMapping("/notifications")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }
    
    @GetMapping("/unreadNotifications")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications() {
        return ResponseEntity.ok(notificationService.getUnReadNotifications());
    }

    @PatchMapping("/markAsRead/{notificationId}")
    public ApiResponseDTO markAsRead(Long notificationId){
        notificationService.markAsRead(notificationId);
        return new ApiResponseDTO(true,"Notification marked as Read",LocalDateTime.now());
    }
    
}
