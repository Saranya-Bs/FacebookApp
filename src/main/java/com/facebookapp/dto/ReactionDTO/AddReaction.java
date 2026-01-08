package com.facebookapp.dto.ReactionDTO;

import com.facebookapp.entities.ReactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddReaction {
    public Long postId;
    public ReactionType reaction;
}
