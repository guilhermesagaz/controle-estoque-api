package com.br.controleestoqueapi.service.movimentoEstoque.impl;

import com.br.controleestoqueapi.domain.dto.LucroDTO;
import com.br.controleestoqueapi.domain.dto.MovimentoEstoqueDTO;
import com.br.controleestoqueapi.domain.entity.MovimentoEstoque;
import com.br.controleestoqueapi.domain.entity.Produto;
import com.br.controleestoqueapi.domain.enums.TipoMovimentacaoEnum;
import com.br.controleestoqueapi.domain.form.MovimentoEstoqueEntradaForm;
import com.br.controleestoqueapi.domain.form.MovimentoEstoqueSaidaForm;
import com.br.controleestoqueapi.repository.MovimentoEstoqueRepository;
import com.br.controleestoqueapi.repository.ProdutoRepository;
import com.br.controleestoqueapi.service.movimentoEstoque.MovimentoEstoqueMapper;
import com.br.controleestoqueapi.service.movimentoEstoque.MovimentoEstoqueService;
import com.br.controleestoqueapi.shared.exception.EntityNotFoundException;
import com.br.controleestoqueapi.shared.exception.RegraDeNegocioException;
import com.br.controleestoqueapi.shared.util.StringUtils;
import com.br.controleestoqueapi.specification.movimentoEstoque.MovimentoEstoqueSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static com.br.controleestoqueapi.domain.enums.TipoMovimentacaoEnum.*;


@Service
@Transactional
@RequiredArgsConstructor
public class MovimentoEstoqueServiceImpl implements MovimentoEstoqueService {

    private final MovimentoEstoqueRepository repository;
    private final MovimentoEstoqueMapper mapper;

    private final ProdutoRepository produtoRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<MovimentoEstoqueDTO> listar(TipoMovimentacaoEnum tipo, String dataVenda, String codigoProduto, Pageable pageable) {
        var spec = MovimentoEstoqueSpecificationBuilder.builder(tipo, dataVenda, codigoProduto);
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    @Override
    public MovimentoEstoqueDTO visualizar(Long id) {
        return mapper.toDto(verificarExistencia(id));
    }

    @Override
    public MovimentoEstoqueDTO cadastrarEntrada(MovimentoEstoqueEntradaForm form) {
        var objectToSave = mapper.toModelEntrada(form);
        var produto = verificarExistenciaProduto(form.getCodigoProduto());
        objectToSave.setTipo(ENTRADA);
        objectToSave.setProduto(produto);

        var objectSaved = repository.save(objectToSave);

        atualizarQuantidadeProduto(objectSaved);

        return mapper.toDto(objectSaved);
    }

    @Override
    public MovimentoEstoqueDTO cadastrarSaida(MovimentoEstoqueSaidaForm form) {
        var objectToSave = mapper.toModelSaida(form);
        var produto = verificarExistenciaProduto(form.getCodigoProduto());

        if (produto.getQuantidadeEstoque() < form.getQuantidade())
            throw new RegraDeNegocioException(String.format("Quantidade de saída %d é maior que a quantidade em estoque do Produto Código %s / Estoque %d", form.getQuantidade(), produto.getCodigo(), produto.getQuantidadeEstoque()));

        objectToSave.setTipo(SAIDA);
        objectToSave.setProduto(produto);

        var objectSaved = repository.save(objectToSave);

        atualizarQuantidadeProduto(objectSaved);

        return mapper.toDto(objectSaved);
    }

    @Override
    public LucroDTO lucroPorProduto(Produto produto) {
        return repository.lucroPorProduto(produto).orElse(LucroDTO.builder().quantidadeTotalSaida(0L).lucroTotal(BigDecimal.valueOf(0)).build());
    }

    @Override
    public Long countQuantidadeSaidaByProduto(Produto produto) {
        return repository.countQuantidadeSaidaByProduto(produto).orElse(0L);
    }

    @Override
    public boolean existeMovimentoProduto(Produto produto) {
        var count = repository.countByProduto(produto);
        return count > 0;
    }

    private MovimentoEstoque verificarExistencia(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(StringUtils.messageEntityNotFound("Movimento Estoque", id)));
    }

    private Produto verificarExistenciaProduto(String codigo) {
        return produtoRepository.findByCodigo(codigo).orElseThrow(() -> new EntityNotFoundException(StringUtils.messageEntityNotFound("Produto", codigo)));
    }

    private void atualizarQuantidadeProduto(MovimentoEstoque object) {
        var produto = object.getProduto();
        switch (object.getTipo()) {
            case ENTRADA:
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + object.getQuantidade());
                break;
            case SAIDA:
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - object.getQuantidade());
                break;
        }
    }

}
