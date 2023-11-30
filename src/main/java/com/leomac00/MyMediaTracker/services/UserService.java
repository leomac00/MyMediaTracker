package com.leomac00.MyMediaTracker.services;

import com.leomac00.MyMediaTracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;
    private String notFoundMessage = "User not found!!!";
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username);

        if(user != null){
            return user;
        } else {
            throw new UsernameNotFoundException(notFoundMessage);
        }
    }
}
