package com.demo.e_commerce.model;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "admin_nav_links")
@Data
public class AdminNavLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String label;      // e.g., "Manage Users"
    private String url;        // e.g., "/admin/users"
    private boolean isActive;
}
