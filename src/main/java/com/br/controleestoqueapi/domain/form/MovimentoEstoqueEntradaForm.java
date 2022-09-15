package com.br.controleestoqueapi.domain.form;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEstoqueEntradaForm {

    @NotNull(message = "Quantidade não pode ser nulo(a) ou vazio(a)")
    @Min(value = 1, message = "Quantidade de entrada não pode ser menor que 1")
    private Long quantidade;

    @NotNull(message = "Produto não pode ser nulo(a) ou vazio(a)")
    @NotEmpty(message = "Produto não pode ser nulo(a) ou vazio(a)")
    private String codigoProduto;

}
