package com.hush0k.shareRoute.gamefication.internal.archetype.repository;

import com.hush0k.shareRoute.gamefication.internal.archetype.domain.Archetype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchetypeRepository extends JpaRepository<Archetype, Long> {

}
