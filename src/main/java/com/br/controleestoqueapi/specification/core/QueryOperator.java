package com.br.controleestoqueapi.specification.core;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QueryOperator {
    IN("IN"),
    LIKE("LIKE"),
    EQUAL("EQUAL"),
    NOT_EQUAL("NOT EQUAL"),
    LESS_THAN("LESS THAN"),
    GREATER_THAN("GREATER THAN"),
    LESS_THAN_OR_EQUAL("LESS THAN OR EQUAL"),
    GREATER_THAN_OR_EQUAL("GREATER THAN OR EQUAL");

    private String description;
}
