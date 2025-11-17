package org.example.gakkoextend.entity;

import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Table(name = "app_user",
        uniqueConstraints = @UniqueConstraint(name = "uk_user_email", columnNames = "email"))
@Builder
@Entity
public class AppUser {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String email;

    private String fullName;

    // to JEST hash BCrypt (np. 60 znaków), nigdy czysty tekst
    @Column(nullable = false, length = 60)
    private String passwordHash;

    @CreationTimestamp
    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id",
                    foreignKey = @ForeignKey(name = "fk_userrole_user")),
            inverseJoinColumns = @JoinColumn(name = "role_id",
                    foreignKey = @ForeignKey(name = "fk_userrole_role")),
            uniqueConstraints = @UniqueConstraint(
                    name = "uk_userrole_user_role",
                    columnNames = {"user_id", "role_id"}
            )
    )
    private Set<Role> roles = new HashSet<>();


    public AppUser() {

    }

    public AppUser(UUID id, String email, String fullName, String passwordHash, OffsetDateTime createdAt, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.passwordHash = passwordHash;
        this.createdAt = createdAt;
        this.roles = roles;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


}
