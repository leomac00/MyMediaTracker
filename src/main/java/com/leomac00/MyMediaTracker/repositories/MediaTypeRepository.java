package com.leomac00.MyMediaTracker.repositories;

import com.leomac00.MyMediaTracker.models.Media;
import com.leomac00.MyMediaTracker.models.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MediaTypeRepository extends JpaRepository <MediaType, Long> {

    Optional<MediaType> findByIdAndDeletedFalse(Long id);
}
