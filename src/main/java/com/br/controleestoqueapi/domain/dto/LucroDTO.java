package com.br.controleestoqueapi.domain.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LucroDTO {
    private Long quantidadeTotalSaida;
    private BigDecimal lucroTotal;
}
