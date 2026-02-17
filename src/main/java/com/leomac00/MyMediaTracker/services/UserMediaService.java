package com.leomac00.MyMediaTracker.services;

import com.leomac00.MyMediaTracker.data.dtos.MediaDto;
import com.leomac00.MyMediaTracker.data.enums.ErrorMessage;
import com.leomac00.MyMediaTracker.data.mappers.MediaMapper;
import com.leomac00.MyMediaTracker.exceptions.ResourceNotFoundException;
import com.leomac00.MyMediaTracker.models.Media;
import com.leomac00.MyMediaTracker.models.User;
import com.leomac00.MyMediaTracker.models.UserMedia;
import com.leomac00.MyMediaTracker.repositories.MediaRepository;
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
    MediaRepository mediaRepository;
    @Autowired
    MediaMapper mediaMapper;

    public List<MediaDto> getMediaForUser(String username) throws ResourceNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));

        return findMediaDtoByUserId(user.getId());
    }

    public List<MediaDto> addMediaForUser(String username, List<Long> mediaIds) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND.getMessage()));
        List<Media> mediasToBeAdded = mediaRepository.findAllByIdInAndDeletedFalse(mediaIds).orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.MEDIA_NOT_FOUND.getMessage()));
        if(mediasToBeAdded.isEmpty()){
            throw new ResourceNotFoundException(ErrorMessage.OPTION_REQUIRED.getMessage());
        }

        List<Long> existingMediaIds = userMediaRepository.findByUserIdAndDeletedFalse(user.getId())
                .stream()
                .map(m -> m.getMedia().getId())
                .toList();
        List<UserMedia> relationshipsToBeAdded = mediasToBeAdded.stream()
                .filter(m -> !existingMediaIds.contains(m.getId()))
                .map(media -> new UserMedia(user, media, false))
                .toList();

        userMediaRepository.saveAll(relationshipsToBeAdded);

        return findMediaDtoByUserId(user.getId());
    }

    // Helpers
    private List<MediaDto> findMediaDtoByUserId(Long id){
        return userMediaRepository.findByUserIdAndDeletedFalse(id).stream().map(um -> mediaMapper.toDto(um.getMedia())).toList();
    }
}
