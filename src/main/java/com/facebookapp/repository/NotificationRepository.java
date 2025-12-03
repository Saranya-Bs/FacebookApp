package com.facebookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebookapp.entities.Notifications;

public interface NotificationRepository extends JpaRepository<Notifications,Long>{
    
}
