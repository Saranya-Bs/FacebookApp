package com.facebookapp.dto.PostDTO;

import java.time.LocalDateTime;

import com.facebookapp.entities.PostStatus;

public class PostResponse {
    public Long id;
    public Long userId;
    public String post;
    public PostStatus postStatus;
    public LocalDateTime createdAt;
}
