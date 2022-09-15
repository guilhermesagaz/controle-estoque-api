package com.br.controleestoqueapi.specification.produto;

import com.br.controleestoqueapi.domain.entity.Produto;
import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import com.br.controleestoqueapi.shared.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class ProdutoSpecificationBuilder {

    public static Specification<Produto> builder(TipoProdutoEnum tipo, String codigo) {
        Specification<Produto> specification = null;

        specification = filtroTipo(tipo, specification);
        specification = filtroCodigo(codigo, specification);

        return specification;
    }

    private static Specification<Produto> filtroTipo(TipoProdutoEnum tipo, Specification<Produto> specification) {
        if(tipo != null) {
            return ProdutoSpecification.verificarSpecification(specification, ProdutoSpecification.tipo(tipo), "and");
        }
        return specification;
    }

    private static Specification<Produto> filtroCodigo(String codigo, Specification<Produto> specification) {
        if(StringUtils.isNotEmpty(codigo)) {
            return ProdutoSpecification.verificarSpecification(specification, ProdutoSpecification.codigo(codigo), "and");
        }
        return specification;
    }

}
