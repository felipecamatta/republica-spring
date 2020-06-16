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
public class TarefaResponse {

    private Long id;

    private Long republicaId;

    private Date dataAgendamento;

    private String descricao;

    private Date dataTermino;

    private boolean finalizada;

    private List<MoradorTarefaDto> moradorTarefasDto = new ArrayList<>();

}
