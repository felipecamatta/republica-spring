package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarefaRequest {

    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;

    @FutureOrPresent(message = "Forneça uma data futura")
    @NotNull(message = "Data de término é obrigatório")
    private Date dataTermino;

    @NotNull(message = "Forneça os moradores que irão realizar a tarefa")
    private Iterable<Long> moradoresIds;

}
