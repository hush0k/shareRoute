package com.hush0k.shareRoute.gamefication.internal.archetype.repository;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;
import com.hush0k.shareRoute.gamefication.internal.archetype.domain.ArchetypeTranslation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ArchetypeTranslationRepository extends JpaRepository<ArchetypeTranslation, Long> {
    @EntityGraph(attributePaths = "archetype")
    Page<ArchetypeTranslation> findAllByLanguageCode(String languageCode, Pageable pageable);

    @EntityGraph(attributePaths = "archetype")
    Page<ArchetypeTranslation> findAllByArchetypeId(Long archetypeId, Pageable pageable);

    List<ArchetypeTranslation> findAllByArchetypeIdInAndLanguageCode(
            Collection<Long> archetypeIds, String languageCode);

    @EntityGraph(attributePaths = "archetype")
    Optional<ArchetypeTranslation> findByArchetypeIdAndLanguageCode(
            Long archetypeId, String languageCode);





}
