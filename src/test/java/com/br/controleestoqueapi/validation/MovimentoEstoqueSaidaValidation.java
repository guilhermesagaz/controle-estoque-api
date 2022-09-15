package com.br.controleestoqueapi.validation;

import com.br.controleestoqueapi.domain.form.MovimentoEstoqueSaidaForm;
import com.br.controleestoqueapi.fixtures.MovimentoEstoqueSaidaFixtures;
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

public class MovimentoEstoqueSaidaValidation {
    private final MovimentoEstoqueSaidaFixtures fixtures = new MovimentoEstoqueSaidaFixtures();

    @Test
    void comValoresValidos() {
        MovimentoEstoqueSaidaForm form = fixtures.valid();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<MovimentoEstoqueSaidaForm>> constraintViolations =
                validator.validate(form);

        assertThat(constraintViolations.size()).isZero();
    }

    @Test
    static Stream<Arguments> porCamposInvalidos() {
        return Stream.of(
                Arguments.of("valorVenda", null),
                Arguments.of("dataVenda", null),
                Arguments.of("quantidade", null),
                Arguments.of("codigoProduto", "")
        );
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("porCamposInvalidos")
    void comValoresInvalidos(String fieldName, Object invalidValue) {
        MovimentoEstoqueSaidaForm form = fixtures.valid();

        Field field = MovimentoEstoqueSaidaForm.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(form, invalidValue);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        final Validator validator = factory.getValidator();

        Set<ConstraintViolation<MovimentoEstoqueSaidaForm>> constraintViolations = validator.validate(form);

        assertThat(constraintViolations.size()).isGreaterThanOrEqualTo(1);
    }
}
