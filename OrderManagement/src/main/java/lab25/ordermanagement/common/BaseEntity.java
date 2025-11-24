package lab25.ordermanagement.common;

import jakarta.persistence.*;
import lab25.ordermanagement.common.config.UserToken;

import java.time.Instant;

@MappedSuperclass
public abstract class BaseEntity {
    @Column(nullable = false)
    private Instant updatedAt;

    @Column(nullable = false)
    private Instant createdAt;

    @Column
    private String createdBy;

    @Column
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        if (createdAt == null) {
            this.createdAt = now;
        }
        if (updatedAt == null) {
            this.updatedAt = now;
        }

        String currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            if (createdBy == null) {
                this.createdBy = currentUserId;
            }
            if (updatedBy == null) {
                this.updatedBy = currentUserId;
            }
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
        String currentUserId = getCurrentUserId();
        if (currentUserId != null) {
            this.updatedBy = currentUserId;
        }
    }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    private String getCurrentUserId() {
        UserToken token = UserTokenContext.getCurrentUserToken();
        if (token == null) { return null; }
        return token.userId();
    }
}
