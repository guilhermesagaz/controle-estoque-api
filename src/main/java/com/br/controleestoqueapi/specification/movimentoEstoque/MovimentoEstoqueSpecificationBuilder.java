package com.br.controleestoqueapi.specification.movimentoEstoque;

import com.br.controleestoqueapi.domain.entity.MovimentoEstoque;
import com.br.controleestoqueapi.domain.enums.TipoMovimentacaoEnum;
import com.br.controleestoqueapi.shared.util.DataUtils;
import com.br.controleestoqueapi.shared.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class MovimentoEstoqueSpecificationBuilder {

    public static Specification<MovimentoEstoque> builder(TipoMovimentacaoEnum tipo, String dataVenda, String codigoProduto) {
        Specification<MovimentoEstoque> specification = null;

        specification = filtroTipo(tipo, specification);
        specification = filtroDataVenda(dataVenda, specification);
        specification = filtroCodigoProduto(codigoProduto, specification);

        return specification;
    }

    private static Specification<MovimentoEstoque> filtroTipo(TipoMovimentacaoEnum tipo, Specification<MovimentoEstoque> specification) {
        if(tipo != null) {
            return MovimentoEstoqueSpecification.verificarSpecification(specification, MovimentoEstoqueSpecification.tipo(tipo), "and");
        }
        return specification;
    }

    private static Specification<MovimentoEstoque> filtroDataVenda(String dataVenda, Specification<MovimentoEstoque> specification) {
        if(StringUtils.isNotEmpty(dataVenda)) {
            return MovimentoEstoqueSpecification.verificarSpecification(specification, MovimentoEstoqueSpecification.dataVenda(DataUtils.stringToLocalDate(dataVenda)), "and");
        }
        return specification;
    }

    private static Specification<MovimentoEstoque> filtroCodigoProduto(String codigoProduto, Specification<MovimentoEstoque> specification) {
        if(StringUtils.isNotEmpty(codigoProduto)) {
            return MovimentoEstoqueSpecification.verificarSpecification(specification, MovimentoEstoqueSpecification.codigoProduto(codigoProduto), "and");
        }
        return specification;
    }

}
