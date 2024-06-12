package com.example.bookingapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
@ToString
public class Role extends BaseEntity {

    @Column(name = "role_type")
    @Enumerated(EnumType.STRING)
    public RoleType roleType;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<User> users = new HashSet<>();
}
