package com.br.controleestoqueapi.domain.form;

import com.br.controleestoqueapi.shared.validator.data.StringAsLocalDateValid;
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
public class MovimentoEstoqueSaidaForm {

    @NotNull(message = "Valor da venda não pode ser nulo(a) ou vazio(a)")
    @Min(value = 0, message = "Valor de venda não pode ser menor que 0")
    private BigDecimal valorVenda;

    @NotNull(message = "Data da venda não pode ser nulo(a) ou vazio(a)")
    @StringAsLocalDateValid
    private String dataVenda;

    @NotNull(message = "Quantidade não pode ser nulo(a) ou vazio(a)")
    @Min(value = 1, message = "Quantidade de saída não pode ser menor que 1")
    private Long quantidade;

    @NotNull(message = "Produto não pode ser nulo(a) ou vazio(a)")
    @NotEmpty(message = "Produto não pode ser nulo(a) ou vazio(a)")
    private String codigoProduto;

}
