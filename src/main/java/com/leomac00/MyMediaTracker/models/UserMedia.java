package com.leomac00.MyMediaTracker.models;

import com.leomac00.MyMediaTracker.models.compositeKeys.UserMediaId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "user_media")
@NoArgsConstructor
public class UserMedia implements Serializable {
    @EmbeddedId
    private UserMediaId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_user")
    private User user;

    @Getter
    @ManyToOne
    @MapsId("mediaId")
    @JoinColumn(name = "id_media")
    private Media media;

    @Column(name = "deleted")
    private boolean deleted;

    public UserMedia(User user, Media media, Boolean deleted) {
        this.id = new UserMediaId(user.getId(),media.getId());
        this.user = user;
        this.media = media;
        this.deleted = false;
    }
}
