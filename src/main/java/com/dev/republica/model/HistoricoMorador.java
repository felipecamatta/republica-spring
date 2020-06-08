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
public class HistoricoMorador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @NotNull
    private Morador morador;

    @ManyToOne
    @NotNull
    private Republica republica;

    @NotNull
    private Date dataEntrada;

    private Date dataSaida;

    public HistoricoMorador(@NotNull Morador morador, @NotNull Republica republica) {
        this.morador = morador;
        this.republica = republica;
        this.dataEntrada = new Date();
    }

}
