package com.hush0k.shareRoute.gamefication.internal.archetype.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="archetype_translation", schema = "gamefication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ArchetypeTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archetype_id", nullable = false)
    private Archetype archetype;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "language_code")
    private String languageCode;

}
