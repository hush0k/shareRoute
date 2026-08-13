package com.hush0k.shareRoute.auth.internal.domins;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Table(name = "refresh_token", schema = "auth")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "token_hash")
    private String tokenHash;

    @Column(name = "device_info")
    private String deviceInfo;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "is_revoked")
    private Boolean isRevoked;

    @Column(name = "created_at")
    private Instant createdAt;


    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
    }

}
