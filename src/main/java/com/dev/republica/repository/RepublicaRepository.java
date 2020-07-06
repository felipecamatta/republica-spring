package com.dev.republica.repository;

import com.dev.republica.model.Republica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepublicaRepository extends JpaRepository<Republica, Long> {

    Page<Republica> findByNumeroVagasDisponiveisGreaterThanAndNomeContainingIgnoreCaseAndVantagensContainingIgnoreCaseAndAndValorMedioDespesasBetween(
            int numeroVagasDisponiveis, String nome, String vantagens, float despesaMin, float despesaMax, Pageable pageable);

}
