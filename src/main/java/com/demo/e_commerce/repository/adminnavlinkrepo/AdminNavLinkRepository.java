package com.demo.e_commerce.repository.adminnavlinkrepo;

import com.demo.e_commerce.model.AdminNavLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminNavLinkRepository extends JpaRepository<AdminNavLink,Long> {
    List<AdminNavLink> findByIsActiveTrue();
}
