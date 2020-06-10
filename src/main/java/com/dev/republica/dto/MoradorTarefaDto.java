package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoradorTarefaDto {

    private Long moradorId;

    private Long tarefaId;

    private String comentario;

    private boolean finalizada;

}
