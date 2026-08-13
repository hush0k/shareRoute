package com.hush0k.shareRoute.auth.internal.repository;

import com.hush0k.shareRoute.auth.internal.domins.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, UUID> {
}
