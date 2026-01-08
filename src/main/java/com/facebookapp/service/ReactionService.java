package com.facebookapp.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.facebookapp.dto.ReactionDTO.AddReaction;
import com.facebookapp.entities.NotificationType;
import com.facebookapp.entities.Post;
import com.facebookapp.entities.Reaction;
import com.facebookapp.entities.User;
import com.facebookapp.exception.NotFoundException;
import com.facebookapp.repository.PostRepository;
import com.facebookapp.repository.ReactionRepository;
import com.facebookapp.repository.UserRepository;
import com.facebookapp.security.SecurityUtil;

@Service
public class ReactionService {
    
    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public ReactionService(ReactionRepository reactionRepository,
                           PostRepository postRepository,
                           UserRepository userRepository,
                           NotificationService notificationService) {
        this.reactionRepository = reactionRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public void addReaction(AddReaction react){
        User currentUser=userRepository.findByEmail(SecurityUtil.getLoggedInUserEmail()).orElseThrow(()->new NotFoundException("User not found"));
        Post post=postRepository.findById(react.getPostId()).orElseThrow(()->new NotFoundException("Post not found"));
        Reaction reaction=reactionRepository.findByUserIdAndPostId(currentUser, post).orElse(null);

        if(reaction!=null){
            reaction.setReaction(react.getReaction());
            reaction.setUpdatedAt(LocalDateTime.now());
            reactionRepository.save(reaction);
            return;
        }

        Reaction newReaction = new Reaction();
        newReaction.setUserId(currentUser);
        newReaction.setPostId(post);
        newReaction.setReaction(react.reaction);
        newReaction.setCreatedAt(LocalDateTime.now());

        reactionRepository.save(newReaction);

        if (!post.getUserId().getId().equals(currentUser.getId())) {
            notificationService.createNotification(
                    currentUser,
                    post.getUserId(),
                    "Reacted to your post",
                    NotificationType.REACTION
            );
        }
    }
}
