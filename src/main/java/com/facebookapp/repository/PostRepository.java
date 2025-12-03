package com.facebookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebookapp.entities.Post;

public interface PostRepository extends JpaRepository<Post,Long>{

    
} 
