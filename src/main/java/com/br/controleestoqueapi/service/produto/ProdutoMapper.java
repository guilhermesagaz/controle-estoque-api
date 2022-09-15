package com.br.controleestoqueapi.service.produto;

import com.br.controleestoqueapi.domain.dto.ProdutoDTO;
import com.br.controleestoqueapi.domain.entity.Produto;
import com.br.controleestoqueapi.domain.form.ProdutoForm;
import com.br.controleestoqueapi.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProdutoMapper extends EntityMapper<ProdutoDTO, Produto, ProdutoForm> {
}
