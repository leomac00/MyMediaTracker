package com.leomac00.MyMediaTracker.controllers;

import com.leomac00.MyMediaTracker.data.dtos.MediaDto;
import com.leomac00.MyMediaTracker.services.UserMediaService;
import com.leomac00.MyMediaTracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

@RequestMapping("api/userMedia/v1")
public class UserMediaController {

    @Autowired
    private UserMediaService userMediaService;

    @GetMapping("/getMyMedia")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MediaDto>> getMyMedia(@AuthenticationPrincipal(expression = "username") String username){
        return ResponseEntity.ok(userMediaService.getMediaForUser(username));
    }

    private static Authentication getAuth() { return SecurityContextHolder.getContext().getAuthentication();}
}
