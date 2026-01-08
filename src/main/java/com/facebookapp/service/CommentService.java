package com.facebookapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.facebookapp.dto.CommentDTO.CommentResponse;
import com.facebookapp.dto.CommentDTO.CreateComment;
import com.facebookapp.entities.Comment;
import com.facebookapp.entities.NotificationType;
import com.facebookapp.entities.Post;
import com.facebookapp.entities.User;
import com.facebookapp.exception.NotFoundException;
import com.facebookapp.repository.CommentRepository;
import com.facebookapp.repository.PostRepository;
import com.facebookapp.repository.UserRepository;
import com.facebookapp.security.SecurityUtil;

@Service
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public CommentService(CommentRepository commentRepository,
                           PostRepository postRepository,
                           UserRepository userRepository,
                           NotificationService notificationService) {
        this.commentRepository=commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    public CommentResponse addComment(CreateComment comment){
        User currentUser=userRepository.findByEmail(SecurityUtil.getLoggedInUserEmail()).orElseThrow(()->new NotFoundException("User not found"));
        Post post=postRepository.findById(comment.getPostId()).orElseThrow(()->new NotFoundException("Post not found"));

        Comment newComment = new Comment();
        newComment.setUserId(currentUser);
        newComment.setPostId(post);
        newComment.setComment(comment.getComment());
        newComment.setCreatedAt(LocalDateTime.now());

        Comment saved = commentRepository.save(newComment);

        if (!post.getUserId().getId().equals(currentUser.getId())) {
            notificationService.createNotification(
                    currentUser,
                    post.getUserId(),
                    "Commented on your post",
                    NotificationType.COMMENT
            );
        }

        CommentResponse response = new CommentResponse();
        response.id = saved.getId();
        response.userId = currentUser.getId();
        response.postId = post.getId();
        response.comment = saved.getComment();
        response.createdAt = saved.getCreatedAt();

        return response;
    }

    public List<CommentResponse> getComments(Long postId){
        Post post=postRepository.findById(postId).orElseThrow(()->new NotFoundException("Post not found"));

        return commentRepository.findByPostIdAndDeletedAtIsNullOrderByCreatedAtDesc(post)
                                .stream()
                                .map(element->{
                                    return new CommentResponse(element.getId(),element.getUserId().getId(),postId,element.getComment(),element.getCreatedAt());
                                })
                                .toList();                  

    }
}
