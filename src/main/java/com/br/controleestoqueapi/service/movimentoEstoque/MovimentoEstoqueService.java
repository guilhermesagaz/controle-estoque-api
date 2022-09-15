package com.br.controleestoqueapi.service.movimentoEstoque;

import com.br.controleestoqueapi.domain.dto.LucroDTO;
import com.br.controleestoqueapi.domain.dto.MovimentoEstoqueDTO;
import com.br.controleestoqueapi.domain.entity.Produto;
import com.br.controleestoqueapi.domain.enums.TipoMovimentacaoEnum;
import com.br.controleestoqueapi.domain.form.MovimentoEstoqueEntradaForm;
import com.br.controleestoqueapi.domain.form.MovimentoEstoqueSaidaForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MovimentoEstoqueService {
    Page<MovimentoEstoqueDTO> listar(TipoMovimentacaoEnum tipo, String dataVenda, String codigoProduto, Pageable pageable);

    MovimentoEstoqueDTO visualizar(Long id);

    MovimentoEstoqueDTO cadastrarEntrada(MovimentoEstoqueEntradaForm form);

    MovimentoEstoqueDTO cadastrarSaida(MovimentoEstoqueSaidaForm form);

    LucroDTO lucroPorProduto(Produto produto);

    Long countQuantidadeSaidaByProduto(Produto produto);

    boolean existeMovimentoProduto(Produto produto);
}
