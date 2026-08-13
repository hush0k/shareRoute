package com.hush0k.shareRoute.user.internal.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name="users", schema = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserModel {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name="nickname")
    private String nickname;

    @Column(name="email")
    private String email;

    @Column(name="fist_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name="avatar")
    private String avatar;

    @Column(name="description")
    private String description;

    @Column(name="country_id")
    @Builder.Default
    private Integer countryId  = null;

    @Column(name="city_id")
    @Builder.Default
    private Integer cityId = null;

    @Column(name="rank_id")
    @Builder.Default
    private Integer rankId  = null;

    @Column(name="exp")
    @Builder.Default
    private int exp = 0;

    @Column(name="language")
    private String language;

    @Column(name="complete_registration")
    @Builder.Default
    private boolean completeRegistration = false;

    @Column(name="first_enter")
    @Builder.Default
    private boolean firstEnter = true;

    @Column(name="created_at")
    private Instant created_at;

    @Column(name="updated_at")
    private Instant updated_at;


    @PrePersist
    public void prePersist(){
        created_at = Instant.now();
        updated_at = Instant.now();
    }

    @PreUpdate
    public void preUpdate(){
        updated_at = Instant.now();
    }

}
