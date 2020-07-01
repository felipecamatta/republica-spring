package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancaRequest {

    @NotBlank(message = "Tipo é obrigatório")
    private String tipo;

    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;

    @Positive
    @NotNull(message = "Valor é obrigatório")
    private float valor;

    @Min(1)
    @Positive
    @NotNull(message = "Número de parcelas é obrigatório")
    private byte numeroParcelas;

    @FutureOrPresent(message = "Forneça uma data futura")
    @NotNull(message = "Data de vencimento/recebimento é obrigatório")
    private Date dataVencimentoRecebimento;

    @NotNull(message = "Forneça os moradores envolvidos na finança")
    private Iterable<Long> moradoresIds;

}
