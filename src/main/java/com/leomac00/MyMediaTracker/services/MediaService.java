package com.leomac00.MyMediaTracker.services;

import com.leomac00.MyMediaTracker.exceptions.ResourceNotFoundException;
import com.leomac00.MyMediaTracker.models.Media;
import com.leomac00.MyMediaTracker.repositories.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MediaService {
    @Autowired
    private MediaRepository repository;

    public Media findById(Long id){
        return getMediaOrElseThrow(id);
    }

    private Media getMediaOrElseThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Media was not found in the database."));
    }
}
