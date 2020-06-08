package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoRepresentanteResponse {

    private String nomeRepresentante;

    private Date dataInicioMandato;

    private Date dataFimMandato;

}
