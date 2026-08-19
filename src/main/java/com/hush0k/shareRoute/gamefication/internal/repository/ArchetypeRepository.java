package com.hush0k.shareRoute.gamefication.internal.repository;

import com.hush0k.shareRoute.gamefication.internal.domain.ArchetypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArchetypeRepository extends JpaRepository<ArchetypeModel, Long> {

}
