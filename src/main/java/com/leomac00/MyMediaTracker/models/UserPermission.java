package com.leomac00.MyMediaTracker.models;

import com.leomac00.MyMediaTracker.models.compositeKeys.UserMediaId;
import com.leomac00.MyMediaTracker.models.compositeKeys.UserPermissionId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_permission")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPermission {
    @EmbeddedId
    private UserPermissionId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_user")
    private User user;

    @Getter
    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "id_permission")
    private Permission permission;

    @Column(name = "deleted")
    private boolean deleted;
}
