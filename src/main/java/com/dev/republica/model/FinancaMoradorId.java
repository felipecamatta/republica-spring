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
public class FinancaMoradorId implements Serializable {

    @ManyToOne
    private Financa financa;

    @ManyToOne
    private Morador morador;

}
