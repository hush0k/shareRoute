package com.hush0k.shareRoute.auth.internal.domins;

import com.hush0k.shareRoute.auth.internal.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Table(name = "credentials", schema = "auth")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Credentials {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "email")
    private String email;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    @Builder.Default
    private Role role = Role.USER;

    @Column(name = "is_email_verified")
    @Builder.Default
    private Boolean isEmailVerified = false;

    @Column(name = "is_blocked")
    @Builder.Default
    private Boolean isBlocked = false;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;


    @PrePersist
    public void prePersist(){
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    @PreUpdate
    public void preUpdate(){
        this.updatedAt = Instant.now();
    }


}
