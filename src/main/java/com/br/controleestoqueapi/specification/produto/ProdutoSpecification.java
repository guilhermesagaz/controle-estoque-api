package com.br.controleestoqueapi.specification.produto;

import com.br.controleestoqueapi.domain.entity.Produto;
import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import com.br.controleestoqueapi.specification.core.GenericSpecification;
import com.br.controleestoqueapi.specification.core.QueryOperator;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecification extends GenericSpecification<Produto> {

    protected static Specification<Produto> tipo(TipoProdutoEnum tipo) {
        return createSpecification(builderFiltro(QueryOperator.EQUAL, "tipo", tipo));
    }

    protected static Specification<Produto> codigo(String codigo) {
        return createSpecification(builderFiltro(QueryOperator.LIKE, "codigo", codigo));
    }

}
