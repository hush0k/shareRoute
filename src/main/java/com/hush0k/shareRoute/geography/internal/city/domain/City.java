package com.hush0k.shareRoute.geography.internal.city.domain;

import com.hush0k.shareRoute.geography.internal.city.enums.CityType;
import com.hush0k.shareRoute.geography.internal.country.domain.Country;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="city", schema = "geography")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "original_name")
    private String originalName;

    @Enumerated(EnumType.STRING)
    @Column(name = "city_type", nullable = false)
    private CityType cityType;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "timezone")
    private String timezone;

}
