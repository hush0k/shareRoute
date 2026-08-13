package com.hush0k.shareRoute.user.internal.repository;

import com.hush0k.shareRoute.user.internal.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);
    Optional<UserModel> findByNickname(String nickname);
}
