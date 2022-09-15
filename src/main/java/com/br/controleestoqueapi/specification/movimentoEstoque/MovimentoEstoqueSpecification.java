package com.br.controleestoqueapi.specification.movimentoEstoque;

import com.br.controleestoqueapi.domain.entity.MovimentoEstoque;
import com.br.controleestoqueapi.domain.entity.Produto;
import com.br.controleestoqueapi.domain.enums.TipoMovimentacaoEnum;
import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import com.br.controleestoqueapi.specification.core.GenericSpecification;
import com.br.controleestoqueapi.specification.core.QueryOperator;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.time.LocalDate;

public class MovimentoEstoqueSpecification extends GenericSpecification<MovimentoEstoque> {

    protected static Specification<MovimentoEstoque> tipo(TipoMovimentacaoEnum tipo) {
        return createSpecification(builderFiltro(QueryOperator.EQUAL, "tipo", tipo));
    }

    protected static Specification<MovimentoEstoque> dataVenda(LocalDate dataVenda) {
        return createSpecification(builderFiltro(QueryOperator.EQUAL, "dataVenda", dataVenda));
    }

    protected static Specification<MovimentoEstoque> codigoProduto(String codigoProduto) {
        return (Root<MovimentoEstoque> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            Join<MovimentoEstoque, Produto> produtoJoin = root.join("produto");
            query.distinct(true);

            return builder.equal(produtoJoin.get("codigo"), codigoProduto);
        };
    }

}
