package com.hush0k.shareRoute.auth.internal.repository;

import com.hush0k.shareRoute.auth.internal.domins.Credentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CredentialsRepository extends JpaRepository<Credentials, UUID> {
    public Optional<Credentials> findByEmail(String email);
    public Optional<Credentials> findByUserId(UUID userId);
}
