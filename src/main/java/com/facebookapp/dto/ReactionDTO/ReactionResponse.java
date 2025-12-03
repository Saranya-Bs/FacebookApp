package com.facebookapp.dto.ReactionDTO;

import java.time.LocalDateTime;

import com.facebookapp.entities.ReactionType;

public class ReactionResponse {
    public Long id;
    public Long userId;
    public Long postId;
    public ReactionType reaction;
    public LocalDateTime createdAt;
}
