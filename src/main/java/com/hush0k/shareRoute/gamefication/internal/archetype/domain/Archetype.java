package com.hush0k.shareRoute.gamefication.internal.archetype.domain;

import com.hush0k.shareRoute.gamefication.internal.archetype.enums.ArchetypeState;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="archetype", schema = "gamefication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Archetype {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "image")
    private String image;

    @Enumerated(EnumType.STRING)
    @Column(name = "archetype_state")
    @Builder.Default
    private ArchetypeState archetypeState = ArchetypeState.DRAFT;

}
