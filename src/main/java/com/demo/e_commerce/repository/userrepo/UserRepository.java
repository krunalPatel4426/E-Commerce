package com.demo.e_commerce.repository.userrepo;

import com.demo.e_commerce.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsernameOrEmailAndIsDeleted(String username, String email, int isDeleted);

    Boolean existsByUsernameAndIsDeleted(String username, int isDeleted);
    Boolean existsByEmailAndIsDeleted(String email,  int isDeleted);

    Optional<UserEntity> findByIdAndIsDeleted(Long user_id, int isDeleted);
}
