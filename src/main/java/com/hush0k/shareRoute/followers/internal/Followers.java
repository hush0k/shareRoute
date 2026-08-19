package com.hush0k.shareRoute.followers.internal;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="followers", schema = "followers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Followers {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="follower_id")
    private UUID followerId;

    @Column(name="following_id")
    private UUID followingId;

    @Column(name="created_at")
    private Instant createdAt;


    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
    }

}
