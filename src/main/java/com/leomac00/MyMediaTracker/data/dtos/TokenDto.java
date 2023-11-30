package com.leomac00.MyMediaTracker.data.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private Boolean authenticated;
    private String accessToken;
    private String refreshToken;
    private Date created;
    private Date expiration;
}
