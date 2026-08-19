package com.hush0k.shareRoute.gamefication.internal.rank.repository;

import com.hush0k.shareRoute.gamefication.internal.rank.domain.Rank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {
}
