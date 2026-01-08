package com.facebookapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facebookapp.entities.Post;
import com.facebookapp.entities.Reaction;
import com.facebookapp.entities.User;
import java.util.Optional;


public interface ReactionRepository extends JpaRepository<Reaction,Long>{
    Optional<Reaction> findByUserIdAndPostId(User user,Post post);
}
