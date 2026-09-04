package com.hush0k.shareRoute.gamefication.internal.achievement.repository;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementCondition;
import com.hush0k.shareRoute.gamefication.internal.achievement.dto.response.AchievementConditionBriefDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AchievementConditionRepository extends JpaRepository<AchievementCondition, Long> {
    List<AchievementCondition> findAllByAchievementId(UUID achievementId);
}
