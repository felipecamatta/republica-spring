package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String nome;

    private String apelido;

    private String telefone;

    private String linkRedeSocial;

    private String telefoneResponsavel1;

    private String telefoneResponsavel2;

    private String sexo;

    private boolean representante;

    @ManyToOne
    private Republica republica;

}