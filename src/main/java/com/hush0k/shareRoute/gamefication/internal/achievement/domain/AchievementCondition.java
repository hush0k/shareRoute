package com.hush0k.shareRoute.gamefication.internal.achievement.domain;

import com.hush0k.shareRoute.gamefication.internal.achievement.enums.ConditionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name="achievement_condition", schema = "gamefication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AchievementCondition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "achievement_id", nullable = false, unique = true)
    private Achievement achievement;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type")
    private ConditionType conditionType;

    @Column(name = "number_of_action")
    private Long numberOfAction;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private Map<String, Object> metadata;

}
