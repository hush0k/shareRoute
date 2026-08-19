package com.hush0k.shareRoute.gamefication.internal.achievement.domain;

import com.hush0k.shareRoute.gamefication.internal.achievement.enums.ConditionType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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

    @Builder.Default
    @OneToMany(mappedBy = "achievement",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AchievementCondition> condition = new ArrayList<>();

    @Column(name = "exp")
    private Integer exp;

    @Column(name = "image")
    private String image;

}
