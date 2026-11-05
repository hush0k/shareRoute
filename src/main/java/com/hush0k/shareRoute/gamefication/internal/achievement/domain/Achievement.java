package com.hush0k.shareRoute.gamefication.internal.achievement.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="achievement", schema = "gamefication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "level")
    private Integer level;

    @OneToOne(mappedBy = "achievement", cascade = CascadeType.ALL, orphanRemoval = true)
    private AchievementCondition condition;

    @Column(name = "exp")
    private Integer exp;

    @Column(name = "image")
    private String image;

}
