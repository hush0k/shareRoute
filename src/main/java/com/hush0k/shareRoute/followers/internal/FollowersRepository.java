package com.hush0k.shareRoute.followers.internal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface FollowersRepository extends JpaRepository<Followers, UUID> {
    boolean existsByFollowerIdAndFollowingId(UUID followerId, UUID followingId);
    Page<Followers> findAllByFollowerId(UUID followerId, Pageable pageable);
    Page<Followers> findAllByFollowingId(UUID followingId, Pageable pageable);
    @Query("SELECT f.followingId FROM Followers f " +
            "WHERE f.followerId = :currentUserId AND f.followingId IN :followerIds")
    Set<UUID> findFollowingIdsAmong(@Param("currentUserId") UUID currentUserId,
                                    @Param("followerIds") List<UUID> followerIds);

}
