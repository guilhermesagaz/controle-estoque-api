package com.br.controleestoqueapi.fixtures;

import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import com.br.controleestoqueapi.domain.form.ProdutoForm;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdutoFixtures implements ModelFixtures<ProdutoForm> {
    private final Faker FAKER = new Faker(new Locale("pt-BR"));

    @Override
    public ProdutoForm valid() {
        return ProdutoForm.builder()
                .codigo(String.valueOf(FAKER.number().numberBetween(10000000, 99999999)))
                .descricao(FAKER.lorem().characters(10, 200, true))
                .tipo(TipoProdutoEnum.ELETRONICO)
                .valorFornecedor(BigDecimal.valueOf(FAKER.number().randomDouble(2, 100, 10000)))
                .build();
    }

    @Override
    public ProdutoForm invalid() {
        return ProdutoForm.builder()
                .codigo(null)
                .descricao(null)
                .tipo(null)
                .valorFornecedor(null)
                .build();
    }

    @Override
    public List<ProdutoForm> list(Integer size) {
        ArrayList<ProdutoForm> forms = new ArrayList<ProdutoForm>();

        for (int i = 0; i < size; i++) {
            forms.add(valid());
        }

        return forms;
    }
}
