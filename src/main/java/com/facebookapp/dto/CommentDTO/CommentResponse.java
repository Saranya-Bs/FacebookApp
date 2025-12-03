package com.facebookapp.dto.CommentDTO;

import java.time.LocalDateTime;

public class CommentResponse {
    public Long id;
    public Long userId;
    public Long postId;
    public String comment;
    public LocalDateTime createdAt;
}
