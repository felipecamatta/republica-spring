package com.dev.republica.dto;

import com.dev.republica.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepublicaRequest {

    private String nome;

    private Endereco endereco;

    private String vantagens;

    private float valorMedioDespesas;

    private int numeroVagas;

    private String tipoImovel;

    private String genero;

    private String linkEstatuto;

}