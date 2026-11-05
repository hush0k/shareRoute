package com.hush0k.shareRoute.gamefication.internal.achievement.repository;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.AchievementTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AchievementTranslationRepository extends JpaRepository<AchievementTranslation, UUID> {
    AchievementTranslation findByAchievementIdAndLanguageCode(UUID achievementId, String languageCode);
    Optional<AchievementTranslation> findOneByAchievementIdAndLanguageCode(UUID achievementId, String languageCode);
    List<AchievementTranslation> findByAchievementIdInAndLanguageCode(List<UUID> achievementIds, String languageCode);
    Page<AchievementTranslation> findAllByAchievementId(UUID achievementId, Pageable pageable);
}
