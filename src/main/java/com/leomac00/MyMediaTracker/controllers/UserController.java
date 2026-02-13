package com.leomac00.MyMediaTracker.controllers;

import com.leomac00.MyMediaTracker.data.dtos.MediaDto;
import com.leomac00.MyMediaTracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("api/user/v1")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/getMyMedia")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MediaDto>> getMyMedia(){
        Authentication auth = UserController.getAuth();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        return ResponseEntity.ok(service.getMediaForUser(auth.getName()));
    }

    private static Authentication getAuth() { return SecurityContextHolder.getContext().getAuthentication();}

}
