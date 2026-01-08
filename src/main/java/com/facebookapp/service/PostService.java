package com.facebookapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.facebookapp.dto.PostDTO.CreatePost;
import com.facebookapp.dto.PostDTO.PostResponse;
import com.facebookapp.entities.Post;
import com.facebookapp.entities.PostStatus;
import com.facebookapp.entities.User;
import com.facebookapp.exception.UnSupportedOperationException;
import com.facebookapp.exception.NotFoundException;
import com.facebookapp.repository.PostRepository;
import com.facebookapp.repository.UserRepository;
import com.facebookapp.security.SecurityUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostService {
    
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository,UserRepository userRepository){
        this.postRepository=postRepository;
        this.userRepository=userRepository;
    }

    private User getCurrentUser(){
            String email=SecurityUtil.getLoggedInUserEmail();
            return userRepository
                    .findByEmail(email)
                    .orElseThrow(()-> new NotFoundException("User not found with email: "+email));
    }

    public PostResponse createPost(CreatePost post){
        User currentUser=getCurrentUser();

        Post newPost=new Post();
        newPost.setUserId(currentUser);
        newPost.setPost(post.getPost());
        newPost.setPostStatus(PostStatus.ACTIVE);
        newPost.setCreatedAt(LocalDateTime.now());
        newPost.setUpdatedAt(LocalDateTime.now());

        postRepository.save(newPost);

        return new PostResponse(newPost.getId(),newPost.getUserId().getId(),newPost.getPost(),newPost.getPostStatus(),newPost.getCreatedAt());
        
    }

    public List<PostResponse> getPosts(){
        User currentUser=getCurrentUser();
        return postRepository.findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(currentUser)
                .stream()
                .map(ele->{
                    return new PostResponse(ele.getId(), ele.getUserId().getId(), ele.getPost(), ele.getPostStatus(), ele.getCreatedAt());
                })
                .toList();
    }

    public PostResponse hidePost(Long postId){

        log.info("hide post service called ========  {}",postId);
        Post post=postRepository.findById(postId).get();

        log.info("get post id {}",post.getId());
        if(post.equals(null) || post.getDeletedAt()!=null){
            throw new NotFoundException("Post Not Found with id: "+postId);
        }
        if(!post.getUserId().getId().equals(getCurrentUser().getId())){
            throw new UnSupportedOperationException("You do not have access to this Post");
        }
        if(post.getPostStatus().equals(PostStatus.HIDDEN)){
            throw new UnSupportedOperationException("The Post is already hidden");
        }

        post.setPostStatus(PostStatus.HIDDEN);
        postRepository.save(post);
        return new PostResponse(postId, post.getId(), post.getPost(), PostStatus.HIDDEN, LocalDateTime.now());
    }

    public PostResponse unHidePost(Long postId){
        Post post=postRepository.findById(postId).get();
        if(post.equals(null) || post.getDeletedAt()!=null){
            throw new NotFoundException("Post Not Found with id: "+postId);
        }
        if(!post.getUserId().getId().equals(getCurrentUser().getId())){
            throw new UnSupportedOperationException("You do not have access to this Post");
        }
        if(post.getPostStatus().equals(PostStatus.ACTIVE)){
            throw new UnSupportedOperationException("The Post is already Active");
        }

        post.setPostStatus(PostStatus.ACTIVE);
        postRepository.save(post);
        return new PostResponse(postId, post.getId(), post.getPost(), PostStatus.ACTIVE, LocalDateTime.now());
    }

    public void deletePost(Long postId){
        Post post=postRepository.findById(postId).get();
        if(post.equals(null)|| !post.getDeletedAt().equals(null)){
            throw new NotFoundException("Post Not Found with id: "+postId);
        }
        if(!post.getUserId().getId().equals(getCurrentUser().getId())){
            throw new UnSupportedOperationException("You do not have access to this Post");
        }
        post.setDeletedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    public List<PostResponse> getFeed(){
        User user=getCurrentUser();
        return postRepository.findFeed(user.getId())
                .stream()
                .map(p->{
                    PostResponse post=new PostResponse();
                    post.setCreatedAt(p.getCreatedAt());
                    post.setId(p.getId());
                    post.setPost(p.getPost());
                    post.setPostStatus(PostStatus.ACTIVE);
                    post.setUserId(p.getId());
                    return post;
                })
                .toList();
    }
}
