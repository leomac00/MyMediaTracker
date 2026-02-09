package com.leomac00.MyMediaTracker.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "media")
@Data
@Builder
public class Media implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 255, name="title", nullable = false)
    private String title;
    @Column(length = 65534, name="description")
    private String description;
    @Column(length = 2000, name="cover_uri")
    private String coverUri;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "media_type_id", referencedColumnName = "id")
    private MediaType mediaType;
    @Column(nullable = false)
    private Boolean deleted = false;
}
