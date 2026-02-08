package com.leomac00.MyMediaTracker.repositories;

import com.leomac00.MyMediaTracker.models.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaTypeRepository extends JpaRepository <MediaType, Long> {
}
