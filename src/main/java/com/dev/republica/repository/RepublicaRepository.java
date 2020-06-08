package com.dev.republica.repository;

import com.dev.republica.model.Republica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepublicaRepository extends JpaRepository<Republica, Long> {

    List<Republica> findByNumeroVagasDisponiveisGreaterThanAndNomeContainingAndVantagensContaining(
            int numeroVagasDisponiveis, String nome, String vantagens);

}
