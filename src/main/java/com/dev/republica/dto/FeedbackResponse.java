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
public class FeedbackResponse {

    private Long id;

    private String tipo;

    private String descricao;

    private Date dataFeedback;

    private String moradorNome;

    private boolean anonimo;

    private String republicaNome;

    private Date dataSolucao;

    private int idade;

    private String status;

    private List<MoradorResponse> moradores = new ArrayList<>();

}
