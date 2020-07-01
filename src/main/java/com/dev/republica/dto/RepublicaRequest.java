package com.dev.republica.dto;

import com.dev.republica.model.Endereco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepublicaRequest {

    @Size(min = 2, max = 25, message = "Nome deve ter entre 2 e 25 caracteres")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotNull
    private Endereco endereco;

    @NotBlank(message = "Vantagens é obrigatório")
    private String vantagens;

    @NotBlank(message = "Valor médio das despesas é obrigatório")
    private float valorMedioDespesas;

    @NotNull(message = "Número de vagas é obrigatório")
    @Min(value = 2, message = "Não é possível criar uma república com menos de 2 vagas")
    @Max(value = 15, message = "O número máximo de vagas é de 15 pessoas")
    private int numeroVagas;

    @NotBlank(message = "Tipo do imóvel é obrigatório")
    private String tipoImovel;

    @NotBlank(message = "Gênero é obrigatório")
    private String genero;

    @Pattern(regexp = "(/[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
    private String linkEstatuto;

}