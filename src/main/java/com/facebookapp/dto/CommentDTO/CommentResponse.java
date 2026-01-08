package com.facebookapp.dto.CommentDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    public Long id;
    public Long userId;
    public Long postId;
    public String comment;
    public LocalDateTime createdAt;
}
