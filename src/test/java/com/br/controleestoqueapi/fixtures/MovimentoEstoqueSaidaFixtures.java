package com.br.controleestoqueapi.fixtures;

import com.br.controleestoqueapi.domain.form.MovimentoEstoqueSaidaForm;
import com.br.controleestoqueapi.shared.util.DataUtils;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovimentoEstoqueSaidaFixtures implements ModelFixtures<MovimentoEstoqueSaidaForm> {
    private final Faker FAKER = new Faker(new Locale("pt-BR"));

    @Override
    public MovimentoEstoqueSaidaForm valid() {
        return MovimentoEstoqueSaidaForm.builder()
                .valorVenda(BigDecimal.valueOf(FAKER.number().randomDouble(2, 1000, 10000)))
                .dataVenda(LocalDate.now().format(DateTimeFormatter.ofPattern(DataUtils.DEFAULT_FORMAT)))
                .quantidade((long) FAKER.number().numberBetween(1, 100))
                .codigoProduto("1010")
                .build();
    }

    @Override
    public MovimentoEstoqueSaidaForm invalid() {
        return MovimentoEstoqueSaidaForm.builder()
                .valorVenda(null)
                .dataVenda(null)
                .quantidade(null)
                .codigoProduto(null)
                .build();
    }

    @Override
    public List<MovimentoEstoqueSaidaForm> list(Integer size) {
        ArrayList<MovimentoEstoqueSaidaForm> forms = new ArrayList<MovimentoEstoqueSaidaForm>();

        for (int i = 0; i < size; i++) {
            forms.add(valid());
        }

        return forms;
    }
}
