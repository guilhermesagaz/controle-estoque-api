package com.br.controleestoqueapi.domain.form;

import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoForm {

    @NotNull(message = "Código não pode ser nulo(a) ou vazio(a)")
    @NotEmpty(message = "Código não pode ser nulo(a) ou vazio(a)")
    private String codigo;

    @NotNull(message = "Descrição não pode ser nulo(a) ou vazio(a)")
    @NotEmpty(message = "Descrição não pode ser nulo(a) ou vazio(a)")
    private String descricao;

    @NotNull(message = "Tipo do Produto não pode ser nulo(a) ou vazio(a)")
    private TipoProdutoEnum tipo;

    @NotNull(message = "Valor no Fornecedor não pode ser nulo(a) ou vazio(a)")
    @Min(0)
    private BigDecimal valorFornecedor;

}
