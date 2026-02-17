package com.leomac00.MyMediaTracker.models.compositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class UserMediaId implements Serializable {

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_media")
    private Long mediaId;
}


