package com.facebookapp.dto.UserDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.facebookapp.entities.AccountStatus;

public class UserResponse {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;
    public LocalDate dateOfBirth;
    public String profile;
    public AccountStatus accountStatus;
    public LocalDateTime createdAt;
}
