package com.hush0k.shareRoute.auth.internal.domins;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Table(name = "login_attempt", schema = "auth")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class LoginAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "is_successful")
    private boolean isSuccessful;

    @Column(name = "created_at")
    private Instant createdAt;

    @PrePersist
    public void prePersist(){
        createdAt = Instant.now();
    }
}
