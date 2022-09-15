package com.br.controleestoqueapi.specification.core;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Filtro {
    private String chave;
    private Object valor;
    private List<Object> valores;
    private QueryOperator operador;
}
