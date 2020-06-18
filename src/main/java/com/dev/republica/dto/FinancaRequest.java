package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancaRequest {

    private String tipo;

    private String descricao;

    private float valor;

    private byte numeroParcelas;

    private Date dataVencimentoRecebimento;

    private Iterable<Long> moradoresIds;

}
