package com.dev.republica.repository;

import com.dev.republica.model.Financa;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FinancaRepository extends JpaRepository<Financa, Long> {

    List<Financa> findByRepublica(Republica republica);

    @Query("select rd from Financa rd join fetch rd.financaMoradores fm where rd.republica = ?1 and fm.pk.morador = ?2")
    List<Financa> findByRepublicaAndMorador(Republica republica, Morador morador);

    @Query("select rd from Financa rd where rd.republica = ?1 and rd.tipo = ?2 and rd.dataLancamento between ?3 and ?4")
    List<Financa> findByRepublicaAndTipoAndDataLancamentoBetween(Republica republica, String tipo, Date inicio,
                                                                 Date termino);

}
