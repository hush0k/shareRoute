package com.hush0k.shareRoute.gamefication.internal.rank.repository;

import com.hush0k.shareRoute.gamefication.internal.rank.domain.RankTranslation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankTranslationRepository extends JpaRepository<RankTranslation, Long> {

    @Query("select t from RankTranslation t " +
            "join fetch t.rank " +
            "where t.rank.id = :rankId and t.languageCode = :lang")
    Optional<RankTranslation> findByRankIdAndLang(
            @Param("rankId") Long rankId,
            @Param("lang") String languageCode
    );
}
