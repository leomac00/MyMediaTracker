package com.leomac00.MyMediaTracker.repositories;

import com.leomac00.MyMediaTracker.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaRepository extends JpaRepository<Media, Long> {
}
