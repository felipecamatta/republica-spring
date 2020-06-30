package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MoradorTarefa {

    @EmbeddedId
    private MoradorTarefaId pk;

    private String comentario;

    private boolean finalizada;

    private Date dataFinalizada;

    public MoradorTarefa(Morador morador, Tarefa tarefa) {
        pk = new MoradorTarefaId();
        this.pk.setMorador(morador);
        this.pk.setTarefa(tarefa);
        this.finalizada = false;
        this.dataFinalizada = null;
    }

}
