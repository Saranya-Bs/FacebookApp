package com.facebookapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.facebookapp.dto.UserDTO.UserResponse;
import com.facebookapp.entities.AccountStatus;
import com.facebookapp.entities.User;
import com.facebookapp.exception.NotFoundException;
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

    public List<UserResponse> getUsers(){
        return userRepository.findAll()
                .stream()
                .map(element->{
                    UserResponse user=new UserResponse();
                    user.setId(element.getId());
                    user.setEmail(element.getEmail());
                    user.setLastName(element.getLastName());
                    user.setFirstName(element.getFirstName());
                    user.setProfile(element.getProfile());
                    return user;
                })
                .toList();
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
            .orElseThrow(()->new NotFoundException("User not found with id: "+id));
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email)
            .orElseThrow(()->new NotFoundException("User not found with email: "+email));
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
