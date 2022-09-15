package com.br.controleestoqueapi.specification.core;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Locale;

public class GenericSpecification<T> {

    public static Filtro builderFiltro(QueryOperator operador, String chave, Object valor) {
        return Filtro.builder()
                .operador(operador)
                .chave(chave)
                .valor(valor)
                .build();
    }

    public static <T> Specification<T> verificarSpecification(Specification<T> atual, Specification<T> nova, String condicao) {
        if(atual != null) {
            if(condicao.equals("and")) {
                return atual.and(nova);
            } else {
                return atual.or(nova);
            }
        } else {
            return nova;
        }
    }

    protected static <T> Specification<T> createSpecification(Filtro filtro) {
        switch (filtro.getOperador()) {
            case IN:
                return in(filtro.getChave(), filtro.getValores());
            case EQUAL:
                return equals(filtro.getChave(), filtro.getValor());
            case NOT_EQUAL:
                return notEquals(filtro.getChave(), filtro.getValor());
            case LESS_THAN:
                return lessThan(filtro.getChave(), filtro.getValor());
            case GREATER_THAN:
                return greaterThan(filtro.getChave(), filtro.getValor());
            case LESS_THAN_OR_EQUAL:
                return lessThanOrEqualTo(filtro.getChave(), filtro.getValor());
            case GREATER_THAN_OR_EQUAL:
                return greaterThanOrEqualTo(filtro.getChave(), filtro.getValor());
            case LIKE:
                return like(filtro.getChave(), filtro.getValor());
            default:
                throw new RuntimeException("Operation not supported yet");
        }
    }

    protected static <T> Specification<T> between(String key, Object startOfValue, Object endOfValue) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (startOfValue instanceof LocalDate && endOfValue instanceof LocalDate) {
                return builder.between(root.get(key), (LocalDate) startOfValue, (LocalDate) endOfValue);
            } else if (startOfValue instanceof LocalDateTime && endOfValue instanceof LocalDateTime) {
                return builder.between(root.get(key), (LocalDateTime) startOfValue, (LocalDateTime) endOfValue);
            } else {
                throw new IllegalArgumentException("Os valores passados são de tipos diferentes");
            }
        };
    }

    private static <T> Specification<T> like(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.like( builder.lower(root.get(key)), "%"+value.toString().toLowerCase(Locale.ROOT)+"%");
        };
    }

    private static <T> Specification<T> equals(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.equal(root.get(key), value);
        };
    }

    private static <T> Specification<T> notEquals(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.notEqual(root.get(key), value);
        };
    }

    private static <T> Specification<T> in(String key, Collection<Object> values) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.in(root.get(key)).value(values);
        };
    }

    private static <T> Specification<T> greaterThan(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if(value instanceof LocalDate) {
                return builder.greaterThan(root.get(key), (LocalDate) value);
            }

            return ((Specification<T>) gt(key, value)).toPredicate(root, query, builder);
        };
    }

    public static <T> Specification<T> greaterThanOrEqualTo(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return ((Specification<T>) greaterThan(key, value).or(equals(key, value))).toPredicate(root, query, builder);
        };
    }

    private static <T> Specification<T> lessThan(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if(value instanceof LocalDate) {
                return builder.lessThan(root.get(key), (LocalDate) value);
            }

            return ((Specification<T>) lt(key, value)).toPredicate(root, query, builder);
        };
    }

    private static <T> Specification<T> lessThanOrEqualTo(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return ((Specification<T>) lessThan(key, value).or(equals(key, value))).toPredicate(root, query, builder);
        };
    }

    private static <T> Specification<T> gt(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.gt(root.get(key), (Number) value);
        };
    }

    private static <T> Specification<T> lt(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.lt(root.get(key), (Number) value);
        };
    }

    protected static <T> Specification<T> startsWith(String key, Object value) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            return builder.like( builder.lower(root.get(key).as(String.class)), value.toString().toLowerCase(Locale.ROOT)+"%");
        };
    }
}
