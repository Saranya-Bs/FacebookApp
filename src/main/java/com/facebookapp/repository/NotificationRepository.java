package com.facebookapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebookapp.entities.Notifications;
import com.facebookapp.entities.User;

public interface NotificationRepository extends JpaRepository<Notifications,Long>{
    
    List<Notifications> findByReceiverIdOrderByCreatedAtDesc(User receiverId);

    List<Notifications> findByReceiverIdAndIsReadFalse(User receiverId);

    List<Notifications> findAllByReceiverId(User receiverId);

}
