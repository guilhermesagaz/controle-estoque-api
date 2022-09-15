package com.br.controleestoqueapi.controller;

import com.br.controleestoqueapi.domain.dto.MovimentoEstoqueDTO;
import com.br.controleestoqueapi.domain.enums.TipoMovimentacaoEnum;
import com.br.controleestoqueapi.domain.form.MovimentoEstoqueEntradaForm;
import com.br.controleestoqueapi.domain.form.MovimentoEstoqueSaidaForm;
import com.br.controleestoqueapi.service.movimentoEstoque.MovimentoEstoqueService;
import com.br.controleestoqueapi.shared.validator.data.StringAsLocalDateValid;
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
@RequestMapping("/movimento-estoque")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MovimentoEstoqueController {

    private MovimentoEstoqueService service;

    @GetMapping
    public ResponseEntity<Page<MovimentoEstoqueDTO>> listar(
            @RequestParam(required = false) TipoMovimentacaoEnum tipo,
            @RequestParam(required = false) @StringAsLocalDateValid String dataVenda,
            @RequestParam(required = false) String codigoProduto,
            @ParameterObject @PageableDefault(sort = "id",
                    direction = Sort.Direction.DESC) Pageable paginacao) {
        return ResponseEntity.ok(service.listar(tipo, dataVenda, codigoProduto, paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentoEstoqueDTO> visualizar(@PathVariable Long id) {
        return ResponseEntity.ok(service.visualizar(id));
    }

    @PostMapping("/cadastrar-entrada")
    public ResponseEntity<MovimentoEstoqueDTO> cadastrarEntrada(@RequestBody @Valid MovimentoEstoqueEntradaForm form,
                                                UriComponentsBuilder uriBuilder) {
        MovimentoEstoqueDTO dto = service.cadastrarEntrada(form);
        URI uri = uriBuilder.path("/movimento-estoque/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PostMapping("/cadastrar-saida")
    public ResponseEntity<MovimentoEstoqueDTO> cadastrarSaida(@RequestBody @Valid MovimentoEstoqueSaidaForm form,
                                                UriComponentsBuilder uriBuilder) {
        MovimentoEstoqueDTO dto = service.cadastrarSaida(form);
        URI uri = uriBuilder.path("/movimento-estoque/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

}
