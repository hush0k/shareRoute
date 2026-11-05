package com.hush0k.shareRoute.gamefication.internal.achievement.repository;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AchievementConditionRepository extends JpaRepository<AchievementCondition, Long> {
    Optional<AchievementCondition> findByAchievementId(UUID achievementId);
}
