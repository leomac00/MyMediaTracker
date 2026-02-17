package com.leomac00.MyMediaTracker.repositories;

import com.leomac00.MyMediaTracker.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MediaRepository extends JpaRepository<Media, Long> {

    @Modifying
    @Query("UPDATE Media m SET m.deleted = true WHERE m.id = :id AND m.deleted = false")
    int softDeleteById(Long id);

    Optional<Media> findByIdAndDeletedFalse(Long id);

    Optional<List<Media>> findAllByIdInAndDeletedFalse(List<Long> mediaIds);
}
