package com.facebookapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.facebookapp.dto.ApiResponseDTO;
import com.facebookapp.dto.PostDTO.CreatePost;
import com.facebookapp.dto.PostDTO.PostResponse;
import com.facebookapp.service.PostService;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/post")
public class PostController {
    
    private final PostService postService;

    public PostController(PostService postService){
        this.postService=postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody CreatePost post) {
        return ResponseEntity.ok(postService.createPost(post));
    }
    
    @GetMapping("/allposts")
    public ResponseEntity<List<PostResponse>> getPosts() {
        return ResponseEntity.ok(postService.getPosts());
    }
    
    @PatchMapping("/hidePost/{postId}")
    public ResponseEntity<PostResponse> hidePost(@PathVariable Long postId){
        return ResponseEntity.ok(postService.hidePost(postId));
    }

    @PatchMapping("/unhidePost/{postId}")
    public ResponseEntity<PostResponse> unHidePost(@PathVariable Long postId){
        return ResponseEntity.ok(postService.unHidePost(postId));
    }

    @DeleteMapping("/deletePost/{postId}")
    public ApiResponseDTO deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
        return new ApiResponseDTO(true,"Deleted Post successfully",LocalDateTime.now());
    }

    @GetMapping("/feed")
    public ResponseEntity<List<PostResponse>> getFeed() {
        return ResponseEntity.ok(postService.getFeed());
    }
    
}
