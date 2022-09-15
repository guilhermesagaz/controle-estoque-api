package com.br.controleestoqueapi.service;

import com.br.controleestoqueapi.AplicationConfigTest;
import com.br.controleestoqueapi.domain.dto.ProdutoDTO;
import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import com.br.controleestoqueapi.domain.form.ProdutoForm;
import com.br.controleestoqueapi.fixtures.ProdutoFixtures;
import com.br.controleestoqueapi.service.produto.ProdutoMapper;
import com.br.controleestoqueapi.service.produto.ProdutoService;
import com.br.controleestoqueapi.shared.exception.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProdutoServiceTest extends AplicationConfigTest {
    @Autowired
    private ProdutoService service;

    @Autowired
    private ProdutoMapper mapper;

    private ProdutoFixtures fixtures = new ProdutoFixtures();

    @Test
    @DisplayName("Deve listar todos os Produtos cadastrados")
    void listarProdutosTest() {
        Integer size = 10;
        List<ProdutoForm> lista = fixtures.list(size);

        for (ProdutoForm form : lista) {
            Assertions.assertDoesNotThrow(() -> service.cadastrar(form));
        }

        List<ProdutoDTO> serviceList = service.listar();
        assertEquals(size, serviceList.size());
    }

    @Test
    @DisplayName("Deve cadastrar um Produto")
    public void cadastrarProdutoTest() {
        var form = fixtures.valid();

        ProdutoDTO produtoEsperadoDTO = new ProdutoDTO();
        BeanUtils.copyProperties(form, produtoEsperadoDTO);

        ProdutoDTO produtoCadastrado = Assertions.assertDoesNotThrow(
                () -> service.cadastrar(form)
        );

        produtoEsperadoDTO.setId(produtoCadastrado.getId());
        produtoEsperadoDTO.setQuantidadeEstoque(produtoCadastrado.getQuantidadeEstoque());

        assertEquals(produtoEsperadoDTO.getId(), produtoCadastrado.getId());
        assertEquals(produtoEsperadoDTO.getCodigo(), produtoCadastrado.getCodigo());
        assertEquals(produtoEsperadoDTO.getDescricao(), produtoCadastrado.getDescricao());
        assertEquals(produtoEsperadoDTO.getTipo(), produtoCadastrado.getTipo());
        assertEquals(produtoEsperadoDTO.getValorFornecedor(), produtoCadastrado.getValorFornecedor());
        assertEquals(produtoEsperadoDTO.getQuantidadeEstoque(), produtoCadastrado.getQuantidadeEstoque());
    }

    @Test
    @DisplayName("Deve ser possível visualizar um Produto existente")
    public void visualizarProdutoTest() {
        var form = fixtures.valid();

        ProdutoDTO produtoEsperadoDTO = new ProdutoDTO();
        BeanUtils.copyProperties(form, produtoEsperadoDTO);

        ProdutoDTO produtoCadastrado = Assertions.assertDoesNotThrow(
                () -> service.cadastrar(form)
        );

        produtoEsperadoDTO.setId(produtoCadastrado.getId());
        produtoEsperadoDTO.setQuantidadeEstoque(produtoCadastrado.getQuantidadeEstoque());

        ProdutoDTO produtoDTO = service.visualizar(produtoCadastrado.getCodigo());

        assertEquals(produtoEsperadoDTO.getId(), produtoDTO.getId());
        assertEquals(produtoEsperadoDTO.getCodigo(), produtoDTO.getCodigo());
        assertEquals(produtoEsperadoDTO.getDescricao(), produtoDTO.getDescricao());
        assertEquals(produtoEsperadoDTO.getTipo(), produtoDTO.getTipo());
        assertEquals(produtoEsperadoDTO.getValorFornecedor(), produtoDTO.getValorFornecedor());
        assertEquals(produtoEsperadoDTO.getQuantidadeEstoque(), produtoDTO.getQuantidadeEstoque());
    }

    @Test
    @DisplayName("Deve ser possível editar um Produto existente")
    public void editarProdutoTest() {
        String descricao_nova = "Nova descricao para teste";
        BigDecimal valorFornecedor_novo = BigDecimal.valueOf(850);

        var form = fixtures.valid();

        ProdutoDTO produtoCadastrado = Assertions.assertDoesNotThrow(
                () -> service.cadastrar(form)
        );

        form.setDescricao(descricao_nova);
        form.setTipo(TipoProdutoEnum.ELETRODOMESTICO);
        form.setValorFornecedor(valorFornecedor_novo);

        ProdutoDTO produtoAtualizado = Assertions.assertDoesNotThrow(
                () -> service.atualizar(produtoCadastrado.getCodigo(), form)
        );

        assertEquals(produtoCadastrado.getId(), produtoAtualizado.getId());
        assertEquals(produtoCadastrado.getCodigo(), produtoAtualizado.getCodigo());
        assertEquals(descricao_nova, produtoAtualizado.getDescricao());
        assertEquals(TipoProdutoEnum.ELETRODOMESTICO, produtoAtualizado.getTipo());
        assertEquals(valorFornecedor_novo, produtoAtualizado.getValorFornecedor());
    }

    @Test
    @DisplayName("Deve ser possível excluír um Produto")
    public void excluirProdutoTest(){
        var form = fixtures.valid();
        var dto = service.cadastrar(form);
        service.excluir(dto.getCodigo());

        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            service.visualizar(dto.getCodigo());
        });
        Assertions.assertEquals(String.format("Produto não encontrado(a) para o Código %s", dto.getCodigo()), thrown.getMessage());
    }

}
