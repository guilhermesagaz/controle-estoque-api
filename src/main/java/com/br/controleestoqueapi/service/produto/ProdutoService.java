package com.br.controleestoqueapi.service.produto;

import com.br.controleestoqueapi.domain.dto.LucroDTO;
import com.br.controleestoqueapi.domain.dto.LucroProdutoDTO;
import com.br.controleestoqueapi.domain.dto.ProdutoDTO;
import com.br.controleestoqueapi.domain.dto.ProdutoSaidaDTO;
import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import com.br.controleestoqueapi.domain.form.ProdutoForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProdutoService {
    Page<ProdutoDTO> listar(TipoProdutoEnum tipo, String codigo, Pageable pageable);

    List<ProdutoDTO> listar();

    ProdutoDTO visualizar(String codigo);

    ProdutoDTO cadastrar(ProdutoForm form);

    ProdutoDTO atualizar(String codigo, ProdutoForm form);

    ProdutoSaidaDTO consultarProdutoSaida(String codigo);

    LucroProdutoDTO consultarLucroPorProduto(String codigo);

    void excluir(String codigo);
}
