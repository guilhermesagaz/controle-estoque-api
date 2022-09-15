package com.br.controleestoqueapi.domain.dto;

import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    private Long id;
    private String codigo;
    private String descricao;
    private TipoProdutoEnum tipo;
    private BigDecimal valorFornecedor;
    private Long quantidadeEstoque;
}
