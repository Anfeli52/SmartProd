package com.andres.smartprod.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CalculationResult {
    private Double totalPintura;
    private Double totalLavado;
    private Double totalPinturaMin;
    private Double totalHorneo;
    private int pieces;

}
