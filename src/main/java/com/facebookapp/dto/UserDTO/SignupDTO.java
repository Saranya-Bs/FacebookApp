package com.facebookapp.dto.UserDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
