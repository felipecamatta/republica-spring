package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaRequest {

    private String descricao;

    private Date dataTermino;

    private Iterable<Long> moradoresIds;

}
