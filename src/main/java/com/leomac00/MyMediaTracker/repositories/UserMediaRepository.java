package com.leomac00.MyMediaTracker.repositories;

import com.leomac00.MyMediaTracker.models.UserMedia;
import com.leomac00.MyMediaTracker.models.compositeKeys.UserMediaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMediaRepository extends JpaRepository<UserMedia, UserMediaId> {
    List<UserMedia> findByUserIdAndDeletedFalse(Long userId);
}
