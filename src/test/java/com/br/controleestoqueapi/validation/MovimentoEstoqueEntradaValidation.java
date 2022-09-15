package com.br.controleestoqueapi.validation;

import com.br.controleestoqueapi.domain.form.MovimentoEstoqueEntradaForm;
import com.br.controleestoqueapi.fixtures.MovimentoEstoqueEntradaFixtures;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class MovimentoEstoqueEntradaValidation {
    private final MovimentoEstoqueEntradaFixtures fixtures = new MovimentoEstoqueEntradaFixtures();

    @Test
    void comValoresValidos() {
        MovimentoEstoqueEntradaForm form = fixtures.valid();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<MovimentoEstoqueEntradaForm>> constraintViolations =
                validator.validate(form);

        assertThat(constraintViolations.size()).isZero();
    }

    @Test
    static Stream<Arguments> porCamposInvalidos() {
        return Stream.of(
                Arguments.of("quantidade", null),
                Arguments.of("codigoProduto", "")
        );
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("porCamposInvalidos")
    void comValoresInvalidos(String fieldName, Object invalidValue) {
        MovimentoEstoqueEntradaForm form = fixtures.valid();

        Field field = MovimentoEstoqueEntradaForm.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(form, invalidValue);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<MovimentoEstoqueEntradaForm>> constraintViolations = validator.validate(form);

        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);
    }
}
