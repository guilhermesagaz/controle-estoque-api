package com.br.controleestoqueapi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoProdutoEnum {
    ELETRONICO("Eletrônico"),
    ELETRODOMESTICO("Eletrodoméstico"),
    MOVEL("Móvel");

    private String descricao;
}
