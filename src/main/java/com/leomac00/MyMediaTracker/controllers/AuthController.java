package com.leomac00.MyMediaTracker.controllers;

import com.leomac00.MyMediaTracker.data.dtos.AccountCredentialsDto;
import com.leomac00.MyMediaTracker.data.dtos.TokenDto;
import com.leomac00.MyMediaTracker.services.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Authentication")
@RequestMapping("auth")
public class AuthController {
    @Autowired
    AuthService service;

    @PostMapping
    @Operation(
            summary = "Signs in user.",
            description = "This endpoint is used for signin in user.")
    public ResponseEntity signin(@RequestBody AccountCredentialsDto credentials){
        var token = service.signin(credentials);
        return token != null
                ? token
                : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid crdentials!!!");
    }
    @PutMapping(value = "/refresh/{username}")
    @Operation(
            summary = "Signs in user.",
            description = "This endpoint is used for signin in user.")
    public ResponseEntity refreshToken(
            @PathVariable(name = "username") String username,
            @RequestHeader("Authorization") String refreshToken) {

        if (username.isBlank() || refreshToken.isBlank())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");

        var token = service.refreshToken(username, refreshToken);
        return token == null ? ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!") : token;
    }
}
