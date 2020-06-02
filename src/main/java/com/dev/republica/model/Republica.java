package com.dev.republica.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
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

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull
    private Date dataFundacao;

    private Date dataExtincao;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Endereco endereco;

    @NotBlank(message = "Vantagens é obrigatório")
    private String vantagens;

    @NotBlank(message = "Valor médio das despesas é obrigatório")
    private float valorMedioDespesas;

    @NotNull(message = "Número de vagas é obrigatório")
    private int numeroVagas;

    @NotNull
    private int numeroVagasDisponiveis;

    @NotBlank(message = "Tipo do imóvel é obrigatório")
    private String tipoImovel;

    @Pattern(regexp = "(/M|F|O/)", message = "Gênero inválido (M, F, O)")
    @NotBlank(message = "Gênero é obrigatório")
    private String genero;

    @Pattern(regexp = "(/[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
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
        if (this.numeroVagasDisponiveis > 0) {
            boolean add = moradores.add(morador);
            if (add) {
                this.numeroVagasDisponiveis--;
                morador.setRepublica(this);
            }
            return add;
        }
        return false;
    }

    public boolean removerMorador(Morador morador) {
        if (moradores.contains(morador)) {
            if (moradores.remove(morador)) {
                morador.setRepublica(null);
                this.numeroVagasDisponiveis++;
                return true;
            }
        }
        return false;
    }

}