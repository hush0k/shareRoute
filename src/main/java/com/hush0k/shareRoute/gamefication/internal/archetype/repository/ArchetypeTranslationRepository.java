package com.hush0k.shareRoute.gamefication.internal.archetype.repository;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;
import com.hush0k.shareRoute.gamefication.internal.archetype.domain.ArchetypeTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchetypeTranslationRepository extends JpaRepository<ArchetypeTranslation, Long> {
    @Query("select t from ArchetypeTranslation t " +
            "join fetch t.archetype " +
            "where t.languageCode = :lang")
    Page<ArchetypeTranslation> findAllByLang(@Param("lang") String lang, Pageable pageable);

    @Query("select t from ArchetypeTranslation t " +
            "join fetch t.archetype " +
            "where t.archetype.id = :archId")
    Page<ArchetypeTranslation> findAllByArchId(@Param("archId") Long archetypeId, Pageable pageable);

    @Query("select t from ArchetypeTranslation t " +
            "join fetch t.archetype " +
            "where t.archetype.id in :archetypeIds " +
            "and t.languageCode = :languageCode")
    List<ArchetypeTranslation> findAllByArchetypeIdInAndLanguageCode(
            @Param("archetypeIds") List<Long> archetypeIds,
            @Param("languageCode") String languageCode
    );
    Optional<Archetype> findByName(String name);

    @Query("select t from ArchetypeTranslation t " +
            "join fetch t.archetype " +
            "where t.archetype.id = :archId and t.languageCode = :lang")
    Optional<ArchetypeTranslation> findByArchetypeIdAndLanguageCode(
            @Param("archId") Long archetypeId,
            @Param("lang") String languageCode
    );



}
