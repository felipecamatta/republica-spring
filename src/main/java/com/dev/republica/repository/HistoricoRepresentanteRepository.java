package com.dev.republica.repository;

import com.dev.republica.model.HistoricoRepresentante;
import com.dev.republica.model.Republica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoRepresentanteRepository extends JpaRepository<HistoricoRepresentante, Long> {

    List<HistoricoRepresentante> findByRepublica(Republica republica);

    HistoricoRepresentante findTopByRepublicaOrderByIdDesc(Republica republica);

}
