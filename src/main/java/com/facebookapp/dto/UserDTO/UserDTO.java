package com.facebookapp.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

import com.facebookapp.entities.AccountStatus;
import com.facebookapp.entities.Comment;
import com.facebookapp.entities.Post;
import com.facebookapp.entities.Reaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public LocalDate dateOfBirth;
    public String profile;
    public AccountStatus accountStatus;
    public List<Post> posts;
    public List<Comment> comments;
    public List<Reaction> reactions;
    
}
