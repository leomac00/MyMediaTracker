package com.leomac00.MyMediaTracker.services.auth;

import com.leomac00.MyMediaTracker.data.dtos.AccountCredentialsDto;
import com.leomac00.MyMediaTracker.data.dtos.TokenDto;
import com.leomac00.MyMediaTracker.data.enums.ErrorMessage;
import com.leomac00.MyMediaTracker.models.User;
import com.leomac00.MyMediaTracker.repositories.UserRepository;
import com.leomac00.MyMediaTracker.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository repository;
    @Autowired
    JwtProvider provider;
    @Autowired
    AuthenticationManager authManager;

    public ResponseEntity<TokenDto> signin(AccountCredentialsDto credentials){
        try {
            var user = repository.findByUsername(credentials.getUsername()).orElseThrow(() -> new RuntimeException(ErrorMessage.USER_NOT_FOUND.getMessage()));
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword()
                    )
            );
            var tokenDto = new TokenDto();
            if(user != null){

                tokenDto = provider.createAccessToken(
                        user.getUsername(), user.getRoles());
            } else {
                throw new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage());
            }
            return ResponseEntity.ok(tokenDto);
        } catch (Exception e) {
            throw new BadCredentialsException(ErrorMessage.INVALID_CREDENTIALS.getMessage());
        }
    }
    public ResponseEntity refreshToken(String username, String refreshToken) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(ErrorMessage.USER_NOT_FOUND.getMessage()));

        var tokenResponse = new TokenDto();
        if (user != null) {
            tokenResponse = provider.createAccessToken(username, user.getRoles());
        } else {
            throw new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage());
        }
        return ResponseEntity.ok(tokenResponse);
    }
}
