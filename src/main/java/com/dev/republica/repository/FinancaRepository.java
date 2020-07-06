package com.dev.republica.repository;

import com.dev.republica.model.Financa;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FinancaRepository extends JpaRepository<Financa, Long> {

    List<Financa> findByRepublica(Republica republica);

    @Query(value = "select f from Financa f where f.republica = ?1 " +
            "and f.tipo = ?2 and upper(f.descricao) like upper(?3) and f.dataLancamento between ?4 and ?5 "/* +
            "and ((?6 != false and ?6 != true) or f.efetivado = ?6)"*/,
            countQuery = "select count(f) from Financa f where f.republica = ?1 " +
                    "and f.tipo = ?2 and upper(f.descricao) like upper(?3) and f.dataLancamento between ?4 and ?5 "/* +
                    "and ((?6 != false and ?6 != true) or f.efetivado = ?6)"*/)
    Page<Financa> findByRepublica(
            Republica republica, String tipo, String descricao, Date dataInicio, Date dataFim, Boolean efetivado, Pageable pageable);

    @Query(value = "select f from Financa f join fetch f.financaMoradores fm where f.republica = ?1 and fm.pk.morador = ?2 " +
            "and f.tipo = ?3 and upper(f.descricao) like upper(?4) and f.dataLancamento between ?5 and ?6",
            countQuery = "select count(f) from Financa f inner join f.financaMoradores fm where f.republica = ?1 and fm.pk.morador = ?2 " +
                    "and f.tipo = ?3 and upper(f.descricao) like upper (?4) and f.dataLancamento between ?5 and ?6")
    Page<Financa> findByRepublicaAndMorador(
            Republica republica, Morador morador, String tipo, String descricao, Date dataInicio, Date dataFim, Pageable pageable);

    List<Financa> findByRepublicaAndTipoAndDataLancamentoBetween(Republica republica, String tipo, Date inicio,
                                                                 Date termino);

}
