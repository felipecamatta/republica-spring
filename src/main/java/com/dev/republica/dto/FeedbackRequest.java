package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequest {

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;

    @Size(min = 6, max = 120, message = "Descrição deve ter no mínimo 6 caracteres")
    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;

    @NotNull(message = "Anônimo é obrigatório")
    private boolean anonimo;

    @NotNull(message = "Forneça os moradores da reclamação")
    private Iterable<Long> moradoresIds;

}
