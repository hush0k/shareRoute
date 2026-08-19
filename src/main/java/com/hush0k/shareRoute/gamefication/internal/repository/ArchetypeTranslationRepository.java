package com.hush0k.shareRoute.gamefication.internal.repository;

import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeModel;
import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeTranslationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArchetypeTranslationRepository extends JpaRepository<ArchetypeTranslationModel, Long> {
    @Query("select t from ArchetypeTranslationModel t " +
            "join fetch t.archetype " +
            "where t.languageCode = :lang")
    Page<ArchetypeTranslationModel> findAllByLang(@Param("lang") String lang, Pageable pageable);

    @Query("select t from ArchetypeTranslationModel t " +
            "join fetch t.archetype " +
            "where t.archetype.id = :archId")
    Page<ArchetypeTranslationModel> findAllByArchId(@Param("archId") Long archetypeId, Pageable pageable);

    Optional<ArchetypeModel> findByName(String name);



}
