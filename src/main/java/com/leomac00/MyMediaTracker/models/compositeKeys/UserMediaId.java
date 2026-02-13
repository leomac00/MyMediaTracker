package com.leomac00.MyMediaTracker.models.compositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class UserMediaId implements Serializable {

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_media")
    private Long mediaId;
}

