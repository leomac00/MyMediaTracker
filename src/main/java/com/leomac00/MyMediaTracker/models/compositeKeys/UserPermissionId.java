package com.leomac00.MyMediaTracker.models.compositeKeys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserPermissionId {
    @Column(name = "id_user")
    private Long userID;

    @Column(name = "id_permission")
    private Long permission;
}
