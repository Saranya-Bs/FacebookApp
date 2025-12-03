package com.facebookapp.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.facebookapp.entities.AccountStatus;
import com.facebookapp.entities.User;
import com.facebookapp.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public User createUser(User user){
        user.setAccountStatus(AccountStatus.ACTIVE);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public Optional<User> getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User updateProfile(Long userId,String profile){
        User user=userRepository.findById(userId).orElseThrow(()->new RuntimeException("No User with the Id found"));

        user.setProfile(profile);
        user.setUpdatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User deleteUser(Long id){
        User user=userRepository.findById(id).orElseThrow(()->new RuntimeException("User not Found with the Id"));

        user.setDeletedAt(LocalDateTime.now());
        user.setAccountStatus(AccountStatus.INACTIVE);

        return userRepository.save(user);

    }
}
