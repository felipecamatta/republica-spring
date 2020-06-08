package com.dev.republica.repository;

import com.dev.republica.model.HistoricoMorador;
import com.dev.republica.model.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoMoradorRepository extends JpaRepository<HistoricoMorador, Long> {

    List<HistoricoMorador> findByMorador(Morador morador);

    HistoricoMorador findTopByMoradorOrderByIdDesc(Morador morador);

}
