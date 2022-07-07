package com.villvay.blogger.repositories;

import com.villvay.blogger.entities.Role;
import com.villvay.blogger.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
