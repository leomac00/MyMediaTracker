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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping("/superSecretPasswordHashHelper")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> pwHelper(@RequestBody String pwToBe){
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		Pbkdf2PasswordEncoder pbkdf2Encoder =
				new Pbkdf2PasswordEncoder(
						"", 8, 185000,
						Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);

		encoders.put("pbkdf2", pbkdf2Encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
        return ResponseEntity.ok(passwordEncoder.encode(pwToBe));
    }
}
