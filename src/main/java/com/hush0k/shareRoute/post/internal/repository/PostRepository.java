package com.hush0k.shareRoute.post.internal.repository;

import com.hush0k.shareRoute.post.internal.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {
}
