package com.dev.republica.repository;

import com.dev.republica.model.Morador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {

    Page<Morador> findByNomeContainingIgnoreCaseAndApelidoContainingIgnoreCaseAndSexoContainingIgnoreCase(String nome, String apelido, String sexo, Pageable pageable);

}
