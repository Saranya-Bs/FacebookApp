package com.facebookapp.dto;

import java.time.LocalDateTime;

import com.facebookapp.entities.NotificationType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationDTO {
    public Long id;
    public Long senderId;
    public Long receiverId;
    public String notification;
    public NotificationType notificationType;
    public Boolean isRead;
    public LocalDateTime createdAt;
}
