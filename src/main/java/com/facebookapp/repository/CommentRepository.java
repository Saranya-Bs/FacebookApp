package com.facebookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebookapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment,Long>{
    
}
