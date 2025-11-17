package org.example.gakkoextend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role",
        uniqueConstraints = @UniqueConstraint(name = "uk_role_name", columnNames = "name"))
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 32)
    private String name;

    public Role() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public void setId(Long id) {
        this.id = id;
    }
}
