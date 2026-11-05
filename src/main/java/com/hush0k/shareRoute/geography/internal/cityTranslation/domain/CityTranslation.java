package com.hush0k.shareRoute.geography.internal.cityTranslation.domain;

import com.hush0k.shareRoute.geography.internal.city.domain.City;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="city_translation", schema = "geography")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CityTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "name")
    private String name;

}
