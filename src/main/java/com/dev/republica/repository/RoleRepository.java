package com.dev.republica.repository;

import com.dev.republica.model.Authorities;
import com.dev.republica.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Authorities name);

}
