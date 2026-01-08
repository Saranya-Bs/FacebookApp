package com.facebookapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebookapp.entities.Comment;
import com.facebookapp.entities.Post;

public interface CommentRepository extends JpaRepository<Comment,Long>{
    
    List<Comment> findByPostIdAndDeletedAtIsNullOrderByCreatedAtDesc(Post postId);
}
