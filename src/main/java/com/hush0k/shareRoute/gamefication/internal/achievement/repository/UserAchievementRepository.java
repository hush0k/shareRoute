package com.hush0k.shareRoute.gamefication.internal.achievement.repository;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.UserAchievement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievement, UUID> {
    Optional<UserAchievement> findByUserIdAndAchievementId(UUID userId, UUID achievementId);
    Page<UserAchievement> findAllByUserId(UUID userId, Pageable pageable);
}
