package com.hush0k.shareRoute.gamefication.internal.rank.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="rank", schema = "gamefication")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rank {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exp")
    private Integer exp;

    @Column(name = "image")
    private String image;

}
