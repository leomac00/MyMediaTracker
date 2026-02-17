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
    MediaDto toDto(Media media);

    List<MediaDto> toDtoList(List<Media> mediaList);


    // DTO → Entity (mediaType ignored because it must be loaded from DB)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mediaType", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    Media toEntity(MediaDto dto);


    // UPDATE existing entity (this replaces your manual setters)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mediaType", ignore = true) // handled in service
    @Mapping(target = "deleted", ignore = true)
    void updateEntityFromDto(MediaDto dto, @MappingTarget Media entity);
}
