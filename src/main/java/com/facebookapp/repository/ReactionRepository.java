package com.facebookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebookapp.entities.Reaction;

public interface ReactionRepository extends JpaRepository<Reaction,Long>{
    
}
