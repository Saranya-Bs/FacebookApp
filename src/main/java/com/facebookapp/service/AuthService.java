package com.facebookapp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.facebookapp.dto.AuthResponseDTO;
import com.facebookapp.dto.UserDTO.LoginRequestDTO;
import com.facebookapp.dto.UserDTO.SignupDTO;
import com.facebookapp.entities.AccountStatus;
import com.facebookapp.entities.User;
import com.facebookapp.repository.UserRepository;

@Service
    public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager,UserService userService,JwtService jwtService,UserRepository userRepository){
        this.authenticationManager=authenticationManager;
        this.userService=userService;
        this.jwtService=jwtService;
        this.userRepository=userRepository;
    }

    public ResponseEntity<User> signup(SignupDTO signupDto) {
        if(userRepository.findByEmail(signupDto.getEmail()).isPresent()){
            throw new RuntimeException("User already exists with this email");
        }

        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));

        User newUser=new User();
        newUser.setEmail(signupDto.getEmail());
        newUser.setPassword(signupDto.getPassword());
        newUser.setFirstName(signupDto.getFirstName());
        newUser.setLastName(signupDto.getLastName());
        newUser.setAccountStatus(AccountStatus.ACTIVE);
        newUser.setComments(new ArrayList<>());
        newUser.setCreatedAt(LocalDateTime.now());
        

        return ResponseEntity.ok(userRepository.save(newUser));

    }

    public ResponseEntity<AuthResponseDTO> login(LoginRequestDTO loginDto) {
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginDto.email, loginDto.password)
            );
        }catch(BadCredentialsException e){
            throw new RuntimeException("Invalid email or password");
        }

        User user=userService.getUserByEmail(loginDto.email);
        String token=jwtService.generateToken(user);

        return  ResponseEntity.ok(new AuthResponseDTO(token, user));
    }
    
}
