package com.leomac00.MyMediaTracker.services;

import com.leomac00.MyMediaTracker.data.dtos.MediaDto;
import com.leomac00.MyMediaTracker.data.enums.ErrorMessage;
import com.leomac00.MyMediaTracker.data.mappers.MediaMapper;
import com.leomac00.MyMediaTracker.exceptions.ResourceNotFoundException;
import com.leomac00.MyMediaTracker.models.User;
import com.leomac00.MyMediaTracker.repositories.UserMediaRepository;
import com.leomac00.MyMediaTracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMediaService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMediaRepository userMediaRepository;
    @Autowired
    MediaMapper mediaMapper;

    public List<MediaDto> getMediaForUser(String username) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));

        return userMediaRepository.findByUserIdAndDeletedFalse(user.getId()).stream().map(um -> mediaMapper.toDto(um.getMedia())).toList();
    }
}
