package com.dev.republica.repository;

import com.dev.republica.model.Convite;
import com.dev.republica.model.Morador;
import com.dev.republica.model.Republica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConviteRepository extends JpaRepository<Convite, Long> {

    Page<Convite> findByConvidado(Morador morador, Pageable pageable);

    Page<Convite> findByRepublica(Republica republica, Pageable pageable);

}
