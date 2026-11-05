package com.hush0k.shareRoute.post.internal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="post", schema = "post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "user_id")
    UUID userId;

    @Column(name = "city_id")
    Integer cityId;

    @Column(name = "country_id")
    Integer countryId;

    @Column(name = "content", nullable = true)
    String content;

    @ElementCollection
    @CollectionTable(
            name = "post_images",
            schema = "post",
            joinColumns = @JoinColumn(name = "post_id")
    )
    @OrderColumn(name = "position")
    @Builder.Default
    @Column(name = "img_url")
    List<String> imgUrls = new ArrayList<>();

    @Column(name = "shared_count")
    int sharedCount;

    @Builder.Default
    @Column(name = "exp")
    int exp = 20;

    @Column(name = "created_at")
    Instant createdAt;

    @Column(name = "updated_at")
    Instant updatedAt;


    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate(){
        updatedAt = Instant.now();
    }
}
