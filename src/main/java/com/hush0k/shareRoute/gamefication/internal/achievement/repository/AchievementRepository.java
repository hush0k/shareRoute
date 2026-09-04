package com.hush0k.shareRoute.gamefication.internal.achievement.repository;

import com.hush0k.shareRoute.gamefication.internal.achievement.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, UUID> {
}
