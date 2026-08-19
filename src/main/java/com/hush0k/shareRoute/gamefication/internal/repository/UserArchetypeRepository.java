package com.hush0k.shareRoute.gamefication.internal.repository;

import com.hush0k.shareRoute.gamefication.internal.domain.UserArchetype;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserArchetypeRepository extends JpaRepository<UserArchetype, Long> {
    Page<UserArchetype> findByUserId(UUID userId, Pageable pageable);
}
