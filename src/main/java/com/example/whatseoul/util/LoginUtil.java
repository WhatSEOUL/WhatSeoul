package com.example.whatseoul.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUtil {

    public static boolean isLogin(){
        boolean result = true;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof String){
            result = false;
        }

        return result;
    }
}