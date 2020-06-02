package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Morador {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    private User user;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String apelido;

    @Pattern(regexp = "(/([(][0-9]{2}[)])\\s[0-9]{4,5}\\-[0-9]{4}/)", message = "Telefone inválido")
    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Link da rede social é obrigatório")
    private String linkRedeSocial;

    @Pattern(regexp = "(/([(][0-9]{2}[)])\\s[0-9]{4,5}\\-[0-9]{4}/)", message = "Telefone do Responsável(1) inválido")
    @NotBlank(message = "Telefone do responsável(1) é obrigatório")
    private String telefoneResponsavel1;

    @Pattern(regexp = "(/([(][0-9]{2}[)])\\s[0-9]{4,5}\\-[0-9]{4}/)", message = "Telefone do Responsável(2) inválido")
    @NotBlank(message = "Telefone do responsável(2) é obrigatório")
    private String telefoneResponsavel2;

    @NotBlank(message = "Sexo é obrigatório")
    private String sexo;

    private boolean representante;

    @ManyToOne
    private Republica republica;

}