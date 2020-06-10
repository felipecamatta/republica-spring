package com.dev.republica.repository;

import com.dev.republica.model.MoradorTarefa;
import com.dev.republica.model.MoradorTarefaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradorTarefaRepository extends JpaRepository<MoradorTarefa, MoradorTarefaId> {
}
