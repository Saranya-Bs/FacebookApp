package com.facebookapp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.facebookapp.dto.AuthResponseDTO;
import com.facebookapp.dto.UserDTO.LoginRequestDTO;
import com.facebookapp.dto.UserDTO.SignupDTO;
import com.facebookapp.entities.User;
import com.facebookapp.service.AuthService;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
public class AuthController {

        private final AuthService authService;

        public AuthController(AuthService authService){
                this.authService=authService;
        }

        @PostMapping("/signup")
        public ResponseEntity<User> signup(@RequestBody SignupDTO signupDto) {
            return authService.signup(signupDto);
        }

        @PostMapping("/login")
        public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDto) {            
            return authService.login(loginRequestDto);
        }
        
        
   

}
