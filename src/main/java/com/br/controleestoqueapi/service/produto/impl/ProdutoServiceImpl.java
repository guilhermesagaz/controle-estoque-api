package com.br.controleestoqueapi.service.produto.impl;

import com.br.controleestoqueapi.domain.dto.LucroProdutoDTO;
import com.br.controleestoqueapi.domain.dto.ProdutoDTO;
import com.br.controleestoqueapi.domain.dto.ProdutoSaidaDTO;
import com.br.controleestoqueapi.domain.entity.Produto;
import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import com.br.controleestoqueapi.domain.form.ProdutoForm;
import com.br.controleestoqueapi.repository.ProdutoRepository;
import com.br.controleestoqueapi.service.movimentoEstoque.MovimentoEstoqueService;
import com.br.controleestoqueapi.service.produto.ProdutoMapper;
import com.br.controleestoqueapi.service.produto.ProdutoService;
import com.br.controleestoqueapi.shared.exception.EntityNotFoundException;
import com.br.controleestoqueapi.shared.exception.RegraDeNegocioException;
import com.br.controleestoqueapi.shared.util.StringUtils;
import com.br.controleestoqueapi.specification.produto.ProdutoSpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository repository;
    private final ProdutoMapper mapper;

    private final MovimentoEstoqueService movimentoEstoqueService;

    @Transactional(readOnly = true)
    @Override
    public Page<ProdutoDTO> listar(TipoProdutoEnum tipo, String codigo, Pageable pageable) {
        var spec = ProdutoSpecificationBuilder.builder(tipo, codigo);
        return repository.findAll(spec, pageable).map(mapper::toDto);
    }

    @Override
    public List<ProdutoDTO> listar() {
        return mapper.toDto(repository.findAll());
    }

    @Transactional(readOnly = true)
    @Override
    public ProdutoDTO visualizar(String codigo) {
        return mapper.toDto(verificarExistencia(codigo));
    }

    @Override
    public ProdutoDTO cadastrar(ProdutoForm form) {
        var objectToSave = mapper.toModel(form);

        return mapper.toDto(repository.save(objectToSave));
    }

    @Override
    public ProdutoDTO atualizar(String codigo, ProdutoForm form) {
        var objectToUpdate = verificarExistencia(codigo);

        BeanUtils.copyProperties(form, objectToUpdate,"id", "codigo");

        return mapper.toDto(repository.saveAndFlush(objectToUpdate));
    }

    @Override
    public ProdutoSaidaDTO consultarProdutoSaida(String codigo) {
        var produto = verificarExistencia(codigo);
        return ProdutoSaidaDTO.buildProdutoSaidaDTO(produto, movimentoEstoqueService.countQuantidadeSaidaByProduto(produto));
    }

    @Override
    public LucroProdutoDTO consultarLucroPorProduto(String codigo) {
        var produto = verificarExistencia(codigo);
        var lucro = movimentoEstoqueService.lucroPorProduto(produto);

        return LucroProdutoDTO.builder()
                .produto(mapper.toDto(produto))
                .lucro(lucro)
                .build();
    }

    @Override
    public void excluir(String codigo) {
        var object = verificarExistencia(codigo);

        if (object.getQuantidadeEstoque() > 0)
            throw new RegraDeNegocioException(String.format("Produto Código: %s não pode ser removido pois possui %d unidades em estoque.", object.getCodigo(), object.getQuantidadeEstoque()));

        if (movimentoEstoqueService.existeMovimentoProduto(object))
            throw new RegraDeNegocioException(String.format("Produto Código: %s não pode ser removido pois possui movimentos em estoque.", object.getCodigo()));

        repository.delete(object);
    }

    private Produto verificarExistencia(String codigo) {
        return repository.findByCodigo(codigo).orElseThrow(() -> new EntityNotFoundException(StringUtils.messageEntityNotFound("Produto", codigo)));
    }

}
