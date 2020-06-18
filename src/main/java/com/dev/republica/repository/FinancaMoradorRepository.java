package com.dev.republica.repository;

import com.dev.republica.model.FinancaMorador;
import com.dev.republica.model.FinancaMoradorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinancaMoradorRepository extends JpaRepository<FinancaMorador, FinancaMoradorId> {
}
