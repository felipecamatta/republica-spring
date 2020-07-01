package com.dev.republica.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Republica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Date dataFundacao;

    private Date dataExtincao;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Endereco endereco;

    private String vantagens;

    private float valorMedioDespesas;

    private int numeroVagas;

    @NotNull
    private int numeroVagasDisponiveis;

    private String tipoImovel;

    private String genero;

    private String linkEstatuto;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Morador representante;

    @OneToMany(mappedBy = "republica", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Morador> moradores = new ArrayList<>();

    public void alterarRepresentante(Morador novoRepresentante) {
        this.representante.setRepresentante(false);
        this.setRepresentante(novoRepresentante);
        novoRepresentante.setRepresentante(true);
    }

    public boolean adicionarMorador(Morador morador) {
        if (morador.getRepublica() == null) {
            if (this.numeroVagasDisponiveis > 0) {
                boolean add = moradores.add(morador);
                if (add) {
                    this.numeroVagasDisponiveis--;
                    morador.setRepublica(this);
                }
                return add;
            }
        }
        return false;
    }

    public boolean removerMorador(Morador morador) {
        if (!this.getRepresentante().equals(morador)) {
            if (moradores.contains(morador)) {
                if (moradores.remove(morador)) {
                    morador.setRepublica(null);
                    this.numeroVagasDisponiveis++;
                    return true;
                }
            }
        }
        return false;
    }

}