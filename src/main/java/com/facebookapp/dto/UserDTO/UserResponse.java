package com.facebookapp.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public String profile;

}
