package com.facebookapp.security;

import org.springframework.security.core.context.SecurityContextHolder;


public class SecurityUtil {
   
    public static String getLoggedInUserEmail(){
        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

}
