package com.hush0k.shareRoute.user.internal.repository;

import com.hush0k.shareRoute.user.internal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    @Query("select u from User u where lower(u.nickname) = lower(:nickname)")
    Optional<User> findByNickname(@Param("nickname") String nickname);

    @Query("select count(u) > 0 from User u where lower(u.nickname) = lower(:nickname)")
    boolean existsByNickname(@Param("nickname") String nickname);
}
