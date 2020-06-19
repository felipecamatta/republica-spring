package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequest {

    private String tipo;

    private String descricao;

    private boolean anonimo;

    private Iterable<Long> moradoresIds;

}
