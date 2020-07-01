package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Republica republica;

    @NotNull
    private Date dataAgendamento;

    private String descricao;

    private Date dataTermino;

    @NotNull
    private boolean finalizada;

    @OneToMany(mappedBy = "pk.tarefa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MoradorTarefa> moradorTarefas = new ArrayList<>();

    public boolean addMoradorTarefa(MoradorTarefa moradorTarefa) {
        return getMoradorTarefas().add(moradorTarefa);
    }

    public boolean removeMoradorTarefa(Object o) {
        return getMoradorTarefas().remove(o);
    }

}
