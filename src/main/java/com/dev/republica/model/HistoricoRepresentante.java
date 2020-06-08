package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HistoricoRepresentante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @NotNull
    private Republica republica;

    @ManyToOne
    @NotNull
    private Morador representante;

    @NotNull
    private Date dataInicioMandato;

    private Date dataFimMandato;

    public HistoricoRepresentante(@NotNull Republica republica, @NotNull Morador representante) {
        this.republica = republica;
        this.representante = representante;
        this.dataInicioMandato = new Date();
    }

}
