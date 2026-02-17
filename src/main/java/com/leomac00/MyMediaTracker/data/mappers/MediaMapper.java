package com.leomac00.MyMediaTracker.data.mappers;

import com.leomac00.MyMediaTracker.data.dtos.MediaDto;
import com.leomac00.MyMediaTracker.models.Media;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MediaMapper {

    // Entity → DTO
    @Mapping(source = "mediaType.id", target = "mediaTypeId")
    @Mapping(source = "id", target = "mediaId")
    MediaDto toDto(Media media);

    List<MediaDto> toDtoList(List<Media> mediaList);

    // DTO → Entity
    @Mapping(source = "mediaId", target = "id")
    @Mapping(target = "mediaType", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Media toEntity(MediaDto dto);

    // Update existing entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mediaType", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateEntityFromDto(MediaDto dto, @MappingTarget Media entity);
}
