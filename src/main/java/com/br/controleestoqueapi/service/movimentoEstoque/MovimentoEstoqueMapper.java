package com.br.controleestoqueapi.service.movimentoEstoque;

import com.br.controleestoqueapi.domain.dto.MovimentoEstoqueDTO;
import com.br.controleestoqueapi.domain.entity.MovimentoEstoque;
import com.br.controleestoqueapi.domain.form.MovimentoEstoqueEntradaForm;
import com.br.controleestoqueapi.domain.form.MovimentoEstoqueForm;
import com.br.controleestoqueapi.domain.form.MovimentoEstoqueSaidaForm;
import com.br.controleestoqueapi.service.mapper.EntityMapper;
import com.br.controleestoqueapi.shared.util.DataUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MovimentoEstoqueMapper extends EntityMapper<MovimentoEstoqueDTO, MovimentoEstoque, MovimentoEstoqueForm> {
    @Override
    @Mappings({
            @Mapping(target = "dataVenda", source = "dataVenda", dateFormat = DataUtils.DEFAULT_FORMAT),
            @Mapping(target = "valorVenda", source = "valorVenda", numberFormat = "#.##E0")
    })
    MovimentoEstoqueDTO toDto(MovimentoEstoque entity);

    MovimentoEstoque toModelEntrada(MovimentoEstoqueEntradaForm form);

    @Mappings({
            @Mapping(target = "dataVenda", source = "dataVenda", dateFormat = DataUtils.DEFAULT_FORMAT),
            @Mapping(target = "valorVenda", source = "valorVenda", numberFormat = "#.##E0")
    })
    MovimentoEstoque toModelSaida(MovimentoEstoqueSaidaForm form);
}
