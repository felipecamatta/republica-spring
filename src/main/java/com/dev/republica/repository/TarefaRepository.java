package com.dev.republica.repository;

import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import com.dev.republica.model.Tarefa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    Page<Tarefa> findByRepublica(Republica republica, Pageable pageable);

    @Query(value = "select t from Tarefa t join fetch t.moradorTarefas mt where t.republica = ?1 and mt.pk.morador = ?2",
            countQuery = "select count(t) from Tarefa t inner join t.moradorTarefas mt where t.republica = ?1 and mt.pk.morador = ?2")
    Page<Tarefa> findByRepublicaAndMorador(Republica republica, Morador morador, Pageable pageable);

}
