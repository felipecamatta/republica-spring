package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoradorResponse {

    private Long id;

    private Long idRepublica;

    private String nome;

    private String apelido;

    private String telefone;

    private String linkRedeSocial;

    private String telefoneResponsavel1;

    private String telefoneResponsavel2;

    private String sexo;

}