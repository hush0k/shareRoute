package com.hush0k.shareRoute.auth.internal.repository;

import com.hush0k.shareRoute.auth.internal.domins.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshToken, UUID> {
}
