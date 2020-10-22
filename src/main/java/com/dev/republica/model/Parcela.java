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
public class Parcela {

    @EmbeddedId
    private ParcelaId pk;

    private float valor;

    private Date dataVencimento;

    private boolean efetivado;

}
