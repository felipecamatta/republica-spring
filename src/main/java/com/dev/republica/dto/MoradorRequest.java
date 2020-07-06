package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoradorRequest {

    @Size(min = 10, max = 100, message = "Nome deve ter no mínimo 10 caracteres")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String apelido;

    @Pattern(regexp = "([(][0-9]{2}[)])\\s[0-9]{4,5}\\-[0-9]{4}", message = "Telefone inválido")
    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    @NotBlank(message = "Link da rede social é obrigatório")
    private String linkRedeSocial;

    @Pattern(regexp = "([(][0-9]{2}[)])\\s[0-9]{4,5}\\-[0-9]{4}", message = "Telefone do Responsável(1) inválido")
    @NotBlank(message = "Telefone do responsável(1) é obrigatório")
    private String telefoneResponsavel1;

    @Pattern(regexp = "([(][0-9]{2}[)])\\s[0-9]{4,5}\\-[0-9]{4}", message = "Telefone do Responsável(2) inválido")
    @NotBlank(message = "Telefone do responsável(2) é obrigatório")
    private String telefoneResponsavel2;

    @NotBlank(message = "Sexo é obrigatório")
    private String sexo;

}
