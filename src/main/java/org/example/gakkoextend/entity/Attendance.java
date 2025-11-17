package org.example.gakkoextend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "attendance",
        uniqueConstraints = @UniqueConstraint(name = "uk_attendance_session_user",
                columnNames = {"session_id", "user_id"}))
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_attendance_session"))
    private ClassSession session;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_attendance_user"))
    private AppUser user;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime scannedAt;

    private String sourceIp;    // opcjonalnie: @Column(columnDefinition="inet")
    private String userAgent;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setSession(ClassSession session) {
        this.session = session;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public void setScannedAt(OffsetDateTime scannedAt) {
        this.scannedAt = scannedAt;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public UUID getId() {
        return id;
    }

    public ClassSession getSession() {
        return session;
    }

    public AppUser getUser() {
        return user;
    }

    public OffsetDateTime getScannedAt() {
        return scannedAt;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
