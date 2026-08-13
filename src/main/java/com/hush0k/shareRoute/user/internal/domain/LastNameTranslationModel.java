package com.hush0k.shareRoute.user.internal.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "last_name_translation", schema = "reference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LastNameTranslationModel {
    @Id
    private Integer id;

    @Column(name = "last_name_group_id")
    private Integer lastNameGroupId;

    @Column(name = "language_code")
    private String languageCode;

    private String value;
}