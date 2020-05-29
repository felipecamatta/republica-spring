package com.dev.republica.repository;

import com.dev.republica.model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoradorRepository extends JpaRepository<Morador, Long> {

    List<Morador> findByNomeContaining(String nome);

}
