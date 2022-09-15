package com.br.controleestoqueapi.fixtures;

import com.br.controleestoqueapi.domain.form.MovimentoEstoqueEntradaForm;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MovimentoEstoqueEntradaFixtures implements ModelFixtures<MovimentoEstoqueEntradaForm> {
    private final Faker FAKER = new Faker(new Locale("pt-BR"));

    @Override
    public MovimentoEstoqueEntradaForm valid() {
        return MovimentoEstoqueEntradaForm.builder()
                .quantidade((long) FAKER.number().numberBetween(1, 100))
                .codigoProduto("1010")
                .build();
    }

    @Override
    public MovimentoEstoqueEntradaForm invalid() {
        return MovimentoEstoqueEntradaForm.builder()
                .quantidade(null)
                .codigoProduto(null)
                .build();
    }

    @Override
    public List<MovimentoEstoqueEntradaForm> list(Integer size) {
        ArrayList<MovimentoEstoqueEntradaForm> forms = new ArrayList<MovimentoEstoqueEntradaForm>();

        for (int i = 0; i < size; i++) {
            forms.add(valid());
        }

        return forms;
    }
}
