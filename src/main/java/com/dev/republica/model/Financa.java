package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Financa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Republica republica;

    @NotBlank
    private String tipo; // despesa ou receita

    @NotBlank
    private String descricao;

    @NotNull
    private float valor;

    @NotNull
    private Date dataLancamento;

    @NotNull
    private Date dataVencimentoRecebimento;

    @NotNull
    private boolean efetivado;

    @OneToMany(mappedBy = "pk.financa", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FinancaMorador> financaMoradores = new ArrayList<>();

}
