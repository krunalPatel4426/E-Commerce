package com.demo.e_commerce.repository.rolerepo;

import com.demo.e_commerce.enums.ERole;
import com.demo.e_commerce.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(ERole name);
}
