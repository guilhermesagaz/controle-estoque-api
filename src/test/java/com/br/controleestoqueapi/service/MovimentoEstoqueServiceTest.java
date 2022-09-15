package com.br.controleestoqueapi.service;

import com.br.controleestoqueapi.AplicationConfigTest;
import com.br.controleestoqueapi.domain.dto.MovimentoEstoqueDTO;
import com.br.controleestoqueapi.domain.dto.ProdutoDTO;
import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import com.br.controleestoqueapi.domain.form.ProdutoForm;
import com.br.controleestoqueapi.fixtures.MovimentoEstoqueEntradaFixtures;
import com.br.controleestoqueapi.fixtures.MovimentoEstoqueSaidaFixtures;
import com.br.controleestoqueapi.fixtures.ProdutoFixtures;
import com.br.controleestoqueapi.service.movimentoEstoque.MovimentoEstoqueMapper;
import com.br.controleestoqueapi.service.movimentoEstoque.MovimentoEstoqueService;
import com.br.controleestoqueapi.service.produto.ProdutoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MovimentoEstoqueServiceTest extends AplicationConfigTest {
    @Autowired
    private MovimentoEstoqueService service;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private MovimentoEstoqueMapper mapper;

    private MovimentoEstoqueEntradaFixtures entradaFixtures = new MovimentoEstoqueEntradaFixtures();
    private MovimentoEstoqueSaidaFixtures saidaFixtures = new MovimentoEstoqueSaidaFixtures();

    private ProdutoFixtures produtoFixtures = new ProdutoFixtures();
    private ProdutoDTO produtoDTO;

    @BeforeEach
    void setup() {
        this.produtoDTO = cadastrarProduto();
    }

    @Test
    @DisplayName("Deve cadastrar um Movimento de Entrada")
    public void cadastrarEntradaTest() {
        var form = entradaFixtures.valid();
        form.setCodigoProduto(produtoDTO.getCodigo());

        MovimentoEstoqueDTO movimentoEstoqueDTO = new MovimentoEstoqueDTO();
        BeanUtils.copyProperties(form, movimentoEstoqueDTO);

        MovimentoEstoqueDTO movimentoCadastrado = Assertions.assertDoesNotThrow(
                () -> service.cadastrarEntrada(form)
        );

        movimentoEstoqueDTO.setId(movimentoCadastrado.getId());
        movimentoEstoqueDTO.setTipo(movimentoCadastrado.getTipo());
        movimentoEstoqueDTO.setProduto(movimentoCadastrado.getProduto());


        assertEquals(movimentoEstoqueDTO.getId(), movimentoCadastrado.getId());
        assertEquals(movimentoEstoqueDTO.getTipo(), movimentoCadastrado.getTipo());
        assertEquals(movimentoEstoqueDTO.getValorVenda(), movimentoCadastrado.getValorVenda());
        assertEquals(movimentoEstoqueDTO.getDataVenda(), movimentoCadastrado.getDataVenda());
        assertEquals(movimentoEstoqueDTO.getQuantidade(), movimentoCadastrado.getQuantidade());
        assertEquals(movimentoEstoqueDTO.getProduto().getCodigo(), movimentoCadastrado.getProduto().getCodigo());
    }

    @Test
    @DisplayName("Deve cadastrar um Movimento de Saída")
    public void cadastrarSaidaTest() {
        var form = saidaFixtures.valid();
        form.setCodigoProduto(produtoDTO.getCodigo());
        form.setQuantidade(1L);
        form.setValorVenda(BigDecimal.valueOf(1500));

        var formEntrada = entradaFixtures.valid();
        formEntrada.setCodigoProduto(produtoDTO.getCodigo());

        Assertions.assertDoesNotThrow(() -> service.cadastrarEntrada(formEntrada));

        MovimentoEstoqueDTO movimentoEstoqueDTO = new MovimentoEstoqueDTO();
        BeanUtils.copyProperties(form, movimentoEstoqueDTO);

        MovimentoEstoqueDTO movimentoCadastrado = Assertions.assertDoesNotThrow(
                () -> service.cadastrarSaida(form)
        );

        movimentoEstoqueDTO.setId(movimentoCadastrado.getId());
        movimentoEstoqueDTO.setTipo(movimentoCadastrado.getTipo());
        movimentoEstoqueDTO.setDataVenda(movimentoCadastrado.getDataVenda());
        movimentoEstoqueDTO.setProduto(movimentoCadastrado.getProduto());


        assertEquals(movimentoEstoqueDTO.getId(), movimentoCadastrado.getId());
        assertEquals(movimentoEstoqueDTO.getTipo(), movimentoCadastrado.getTipo());
        assertEquals(movimentoEstoqueDTO.getValorVenda(), movimentoCadastrado.getValorVenda());
        assertEquals(movimentoEstoqueDTO.getDataVenda(), movimentoCadastrado.getDataVenda());
        assertEquals(movimentoEstoqueDTO.getQuantidade(), movimentoCadastrado.getQuantidade());
        assertEquals(movimentoEstoqueDTO.getProduto().getCodigo(), movimentoCadastrado.getProduto().getCodigo());
    }

    @Test
    @DisplayName("Deve ser possível visualizar um Movimento existente")
    public void visualizarMovimentoTest() {
        var form = entradaFixtures.valid();
        form.setCodigoProduto(produtoDTO.getCodigo());

        MovimentoEstoqueDTO movimentoEstoqueDTO = new MovimentoEstoqueDTO();
        BeanUtils.copyProperties(form, movimentoEstoqueDTO);

        MovimentoEstoqueDTO movimentoCadastrado = Assertions.assertDoesNotThrow(
                () -> service.cadastrarEntrada(form)
        );

        movimentoEstoqueDTO.setId(movimentoCadastrado.getId());
        movimentoEstoqueDTO.setTipo(movimentoCadastrado.getTipo());
        movimentoEstoqueDTO.setProduto(movimentoCadastrado.getProduto());

        MovimentoEstoqueDTO movimentoEstoque = service.visualizar(movimentoCadastrado.getId());


        assertEquals(movimentoEstoqueDTO.getId(), movimentoEstoque.getId());
        assertEquals(movimentoEstoqueDTO.getTipo(), movimentoEstoque.getTipo());
        assertEquals(movimentoEstoqueDTO.getValorVenda(), movimentoEstoque.getValorVenda());
        assertEquals(movimentoEstoqueDTO.getDataVenda(), movimentoEstoque.getDataVenda());
        assertEquals(movimentoEstoqueDTO.getQuantidade(), movimentoEstoque.getQuantidade());
        assertEquals(movimentoEstoqueDTO.getProduto().getCodigo(), movimentoEstoque.getProduto().getCodigo());
    }

    private ProdutoDTO cadastrarProduto() {
        var produtoForm = produtoFixtures.valid();
        return Assertions.assertDoesNotThrow(() -> produtoService.cadastrar(produtoForm));
    }

}
