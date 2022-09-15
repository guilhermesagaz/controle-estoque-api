package com.br.controleestoqueapi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoMovimentacaoEnum {
    ENTRADA("Entrada"),
    SAIDA("Saída");

    private String descricao;
}
