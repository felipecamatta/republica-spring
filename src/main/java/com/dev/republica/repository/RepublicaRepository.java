package com.dev.republica.repository;

import com.dev.republica.model.Republica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepublicaRepository extends JpaRepository<Republica, Long> {

    List<Republica> findByNumeroVagasDisponiveisGreaterThanAndNomeContainingAndVantagensContaining(
            int numeroVagasDisponiveis, String nome, String vantagens);

}
