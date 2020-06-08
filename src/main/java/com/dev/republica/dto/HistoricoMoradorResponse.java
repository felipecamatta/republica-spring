package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoMoradorResponse {

    private String republica;

    private String representante;

    private String contatoRepresentante;

    private Date dataEntrada;

    private Date dataSaida;

}
