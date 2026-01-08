package com.facebookapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.facebookapp.entities.Post;
import com.facebookapp.entities.User;

public interface PostRepository extends JpaRepository<Post,Long>{

    @Query("""
            SELECT p FROM Post p
            WHERE p.deletedAt IS NULL
            AND p.postStatus=com.facebookapp.entities.PostStatus.ACTIVE
            AND (
            p.userId.id= :userId
            OR p.userId.id IN(
            SELECT fr.receiverId.id FROM FriendRequest fr
            WHERE fr.senderId.id= :userId
              AND fr.requestStatus=com.facebookapp.entities.RequestStatus.ACCEPTED
            UNION
            SELECT fr.senderId.id FROM FriendRequest fr
            WHERE fr.receiverId.id= :userId
              AND fr.requestStatus=com.facebookapp.entities.RequestStatus.ACCEPTED
            ))
            ORDER BY p.createdAt DESC
            """)
    List<Post> findFeed(Long userId);

    List<Post> findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(User userId);
    
} 
