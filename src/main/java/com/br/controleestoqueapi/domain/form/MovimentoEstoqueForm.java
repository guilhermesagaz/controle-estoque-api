package com.br.controleestoqueapi.domain.form;

import com.br.controleestoqueapi.domain.enums.TipoMovimentacaoEnum;
import com.br.controleestoqueapi.shared.validator.data.StringAsLocalDateValid;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimentoEstoqueForm {

    @NotNull(message = "Tipo do Produto não pode ser nulo(a) ou vazio(a)")
    private TipoMovimentacaoEnum tipo;

    @NotNull(message = "Valor Movimentação não pode ser nulo(a) ou vazio(a)")
    @Min(0)
    private BigDecimal valorVenda;

    @StringAsLocalDateValid
    private LocalDate dataVenda;

    @NotNull(message = "Valor Movimentação não pode ser nulo(a) ou vazio(a)")
    private Long quantidade;

    @NotNull(message = "Produto não pode ser nulo(a) ou vazio(a)")
    @NotEmpty(message = "Produto não pode ser nulo(a) ou vazio(a)")
    private String codigoProduto;

}
