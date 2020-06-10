package com.dev.republica.repository;

import com.dev.republica.model.Convite;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConviteRepository extends JpaRepository<Convite, Long> {

    List<Convite> findByConvidado(Morador morador);

    List<Convite> findByRepublica(Republica republica);

}
