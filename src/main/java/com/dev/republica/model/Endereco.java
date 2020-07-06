package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido")
    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Cidade é obrigatório")
    private String cidade;

    @Pattern(regexp = "(A(C|L|P|M))|BA|(CE)|(DF)|(GO)|(ES)|(M(A|T|S|G))|(P(A|B|R|E|I))|(R(J|N|S|O|R))|(S(P|C|E))|(TO)")
    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    private String pontoDeReferencia;

    private String localizacaoGeografica;

}
