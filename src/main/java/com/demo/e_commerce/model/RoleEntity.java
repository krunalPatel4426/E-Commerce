package com.demo.e_commerce.model;

import com.demo.e_commerce.enums.ERole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private ERole name;
}
