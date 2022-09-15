package com.br.controleestoqueapi.controller;

import com.br.controleestoqueapi.domain.dto.LucroProdutoDTO;
import com.br.controleestoqueapi.domain.dto.ProdutoDTO;
import com.br.controleestoqueapi.domain.dto.ProdutoSaidaDTO;
import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import com.br.controleestoqueapi.domain.form.ProdutoForm;
import com.br.controleestoqueapi.service.produto.ProdutoService;
import lombok.AllArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/produto")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProdutoController {

    private ProdutoService service;

    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> listar(
            @RequestParam(required = false) TipoProdutoEnum tipo,
            @RequestParam(required = false) String codigo,
            @ParameterObject @PageableDefault(sort = "id",
                    direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listar(tipo, codigo, paginacao));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<ProdutoDTO> visualizar(@PathVariable String codigo) {
        return ResponseEntity.ok(service.visualizar(codigo));
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> cadastrar(@RequestBody @Valid ProdutoForm form,
                                                UriComponentsBuilder uriBuilder) {
        ProdutoDTO dto = service.cadastrar(form);
        URI uri = uriBuilder.path("/produto/{codigo}").buildAndExpand(dto.getCodigo()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable String codigo,
                                                @RequestBody @Valid ProdutoForm form) {
        return ResponseEntity.ok(service.atualizar(codigo, form));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluir(@PathVariable String codigo) {
        service.excluir(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{codigo}/quantidade-saidas")
    public ResponseEntity<ProdutoSaidaDTO> consultarProdutoSaida(@PathVariable String codigo) {
        return ResponseEntity.ok(service.consultarProdutoSaida(codigo));
    }

    @GetMapping("/{codigo}/lucro")
    public ResponseEntity<LucroProdutoDTO> consultarLucroPorProduto(@PathVariable String codigo) {
        return ResponseEntity.ok(service.consultarLucroPorProduto(codigo));
    }

}
