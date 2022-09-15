package com.br.controleestoqueapi.domain.dto;

import com.br.controleestoqueapi.domain.enums.TipoMovimentacaoEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEstoqueDTO {
    private Long id;
    private TipoMovimentacaoEnum tipo;
    private BigDecimal valorVenda;
    private LocalDate dataVenda;
    private Long quantidade;
    private ProdutoDTO produto;
}
