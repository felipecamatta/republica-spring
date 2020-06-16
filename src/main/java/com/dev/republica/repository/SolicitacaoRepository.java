package com.dev.republica.repository;

import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import com.dev.republica.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    List<Solicitacao> findBySolicitante(Morador morador);

    List<Solicitacao> findByRepublica(Republica republica);

}
