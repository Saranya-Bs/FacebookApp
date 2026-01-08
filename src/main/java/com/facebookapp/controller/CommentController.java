package com.facebookapp.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebookapp.dto.CommentDTO.CommentResponse;
import com.facebookapp.dto.CommentDTO.CreateComment;
import com.facebookapp.service.CommentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    
    private final CommentService commentService;
    public CommentController(CommentService commentService){
        this.commentService=commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponse> addComment(@RequestBody CreateComment request) {
        return ResponseEntity.ok(commentService.addComment(request));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getComments(postId));
    }
}
