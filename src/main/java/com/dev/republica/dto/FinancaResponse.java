package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancaResponse {

    private Long id;

    private Long republicaId;

    private String tipo;

    private Date dataLancamento;

    private String descricao;

    private float valor;

    private Date dataVencimentoRecebimento;

    private boolean efetivado;

    private List<FinancaMoradorDto> financaMoradoresDto = new ArrayList<>();

}
