package com.hush0k.shareRoute.geography.internal.country.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="country", schema = "geography")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Integer id;

    @Column(name = "iso_code")
    String isoCode;

    @Column(name = "phone_code")
    String phoneCode;

    @Column(name = "currency")
    String currency;

}
