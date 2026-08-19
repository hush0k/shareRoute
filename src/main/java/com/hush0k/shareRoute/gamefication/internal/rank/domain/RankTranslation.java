package com.hush0k.shareRoute.gamefication.internal.rank.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="rank_translation", schema = "gamefication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RankTranslation {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rankId", nullable = false)
    private Rank rank;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

}
