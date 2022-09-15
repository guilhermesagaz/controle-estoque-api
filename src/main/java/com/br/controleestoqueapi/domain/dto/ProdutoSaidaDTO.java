package com.br.controleestoqueapi.domain.dto;

import com.br.controleestoqueapi.domain.entity.Produto;
import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoSaidaDTO {
    private Long id;
    private String codigo;
    private String descricao;
    private TipoProdutoEnum tipo;
    private Long quantidadeEstoque;
    private Long quantidadeSaida;

    public static ProdutoSaidaDTO buildProdutoSaidaDTO(Produto entity, Long quantidadeSaida) {
        return ProdutoSaidaDTO.builder()
                .id(entity.getId())
                .codigo(entity.getCodigo())
                .tipo(entity.getTipo())
                .quantidadeEstoque(entity.getQuantidadeEstoque())
                .quantidadeSaida(quantidadeSaida)
                .build();
    }
}
