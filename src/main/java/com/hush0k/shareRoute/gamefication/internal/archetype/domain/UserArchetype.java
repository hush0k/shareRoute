package com.hush0k.shareRoute.gamefication.internal.archetype.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="user_archetype", schema = "gamefication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserArchetype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "archetype_id", nullable = false)
    private ArchetypeModel archetype;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "is_owned")
    @Builder.Default
    private Boolean isOwned = false;

    @Column(name = "model_version")
    private String modelVersion;

    @Column(name = "confidence_score")
    private Double confidenceScore;

    @Column(name = "discovered_at")
    private Instant discoveredAt;

    @Column(name = "unlocked_at")
    private Instant unlockedAt;


    @PrePersist
    public void prePersist(){
        this.discoveredAt = Instant.now();
    }

}
