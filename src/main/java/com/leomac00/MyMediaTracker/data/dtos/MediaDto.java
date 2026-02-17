package com.leomac00.MyMediaTracker.data.dtos;

import com.leomac00.MyMediaTracker.data.dtos.common.BaseDto;
import com.leomac00.MyMediaTracker.models.MediaType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class MediaDto implements BaseDto {
    private Long mediaId;
    private String title;
    private String description;
    private String coverUri;
    private Long mediaTypeId;
}
