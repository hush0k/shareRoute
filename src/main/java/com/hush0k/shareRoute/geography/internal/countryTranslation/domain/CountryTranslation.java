package com.hush0k.shareRoute.geography.internal.countryTranslation.domain;

import com.hush0k.shareRoute.geography.internal.country.domain.Country;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="country_translation", schema = "geography")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CountryTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "name")
    private String name;

}
