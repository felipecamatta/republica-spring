package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FinancaMorador {

    @EmbeddedId
    private FinancaMoradorId pk;

    private float valor;

    private boolean pago;

    private Date dataPagamento;

    public FinancaMorador(Financa financa, Morador morador, float valor) {
        pk = new FinancaMoradorId();
        pk.setFinanca(financa);
        pk.setMorador(morador);
        this.valor = valor;
        this.pago = false;
        this.dataPagamento = null;
    }

}
