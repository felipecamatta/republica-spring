package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConviteResponse {

    Long id;

    String republicaNome;

    String moradorNome;

    String status;

}
