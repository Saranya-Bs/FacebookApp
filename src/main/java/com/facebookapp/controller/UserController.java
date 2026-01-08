package com.facebookapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.facebookapp.dto.UserDTO.UserResponse;
import com.facebookapp.entities.User;
import com.facebookapp.service.UserService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) { 
        log.info("Creating new user with the email: {}",user.getEmail());

        User createdUser=userService.createUser(user);
        log.info("User Created with given email");

        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        log.info("Fetching All Users");
        List<UserResponse> users=userService.getUsers();
        log.info("Users fetched.");
        return ResponseEntity.ok(users);
    }
    
    
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Fetching User with id: {}",id);
        User user=userService.getUserById(id);

        if(user==null){
            log.warn("User not found with the id: {}",id);
            return ResponseEntity.notFound().build();
        }

        log.debug("Fetched User: {}",user);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        log.info("Fetching User with email: {}",email);
        User user=userService.getUserByEmail(email);

        if(user==null){
            log.warn("User not found with the email: {}",email);
            return ResponseEntity.notFound().build();
        }

        log.debug("Fetched user: {}",user);
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody String profile) {
        log.info("Updating Profile of User with id: {}",id);
        return ResponseEntity.ok(userService.updateProfile(id, profile));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        log.info("Deleting user with id:{}",id);
        User deletedUser=userService.deleteUser(id);
        log.info("Soft Delete of the user with id: {} is performed",id);
        return ResponseEntity.ok(deletedUser);
    }


}
