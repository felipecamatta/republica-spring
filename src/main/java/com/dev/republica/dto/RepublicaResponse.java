package com.dev.republica.dto;

import com.dev.republica.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepublicaResponse {

    private Long id;

    private String nome;

    private Date dataFundacao;

    private Date dataExtincao;

    private Endereco endereco;

    private String vantagens;

    private float valorMedioDespesas;

    private int numeroVagas;

    private int numeroVagasDisponiveis;

    private String tipoImovel;

    private String genero;

    private String linkEstatuto;

    private MoradorResponse representante;

    private List<MoradorResponse> moradores = new ArrayList<>();

}
