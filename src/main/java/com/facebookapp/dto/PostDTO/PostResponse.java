package com.facebookapp.dto.PostDTO;

import java.time.LocalDateTime;

import com.facebookapp.entities.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    public Long id;
    public Long userId;
    public String post;
    public PostStatus postStatus;
    public LocalDateTime createdAt;
}
