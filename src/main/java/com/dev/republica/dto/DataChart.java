package com.dev.republica.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataChart {

    private long idRepublica;
    private Map<Integer, Float> despesas;
    private Map<Integer, Float> receitas;

}
