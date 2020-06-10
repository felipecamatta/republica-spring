package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MoradorTarefaId implements Serializable {

    @ManyToOne
    private Morador morador;

    @ManyToOne
    private Tarefa tarefa;

}
