package com.hush0k.shareRoute.gamefication.internal.achievement.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="user_achievement", schema = "gamefication", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "achievement_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserAchievement {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id", nullable = false)
    private Achievement achievement;

    @Builder.Default
    @Column(name = "completed_count")
    private Long completedCount = 0L;

    @Builder.Default
    @Column(name = "is_achieved")
    private Boolean isAchieved = false;

    @Column(name = "achieved_at")
    private Instant achievedAt;

}
