package com.dev.republica.repository;

import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import com.dev.republica.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByRepublica(Republica republica);

    @Query("select t from Tarefa t inner join t.moradorTarefas mt on t.id = mt.pk.tarefa.id where t.republica = ?1 and mt.pk.morador = ?2")
    List<Tarefa> findByRepublicaAndMorador(Republica republica, Morador morador);

}
