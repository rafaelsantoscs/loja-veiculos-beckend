package iam.solucoesdigitais.baseappjwt.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "push_subscriptions")
public class PushSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 512)
    private String endpoint;

    @Column(nullable = false)
    private String p256dhKey;

    @Column(nullable = false)
    private String authKey;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean active = true;

    // Construtores
    public PushSubscription() {}

    public PushSubscription(String username, String endpoint, String p256dhKey, String authKey) {
        this.username = username;
        this.endpoint = endpoint;
        this.p256dhKey = p256dhKey;
        this.authKey = authKey;
        this.createdAt = LocalDateTime.now();
        this.active = true; // ✅ Garantindo que sempre seja TRUE
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getP256dhKey() {
        return p256dhKey;
    }

    public void setP256dhKey(String p256dhKey) {
        this.p256dhKey = p256dhKey;
    }

    public String getAuthKey() {
        return authKey;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
