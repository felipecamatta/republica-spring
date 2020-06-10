package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MoradorTarefa {

    @EmbeddedId
    private MoradorTarefaId pk;

    private String comentario;

    private boolean finalizada;

    public MoradorTarefa(Morador morador, Tarefa tarefa) {
        pk = new MoradorTarefaId();
        this.pk.setMorador(morador);
        this.pk.setTarefa(tarefa);
        this.finalizada = false;
    }

}
