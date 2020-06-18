package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancaMoradorDto {

    private Long financaId;

    private Long moradorId;

    private float valor;

    private boolean pago;

}
