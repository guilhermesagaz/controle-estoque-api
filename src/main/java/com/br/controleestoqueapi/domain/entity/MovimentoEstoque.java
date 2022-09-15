package com.br.controleestoqueapi.domain.entity;

import com.br.controleestoqueapi.domain.enums.TipoMovimentacaoEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_movimento_estoque")
public class MovimentoEstoque extends GenericModel {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoMovimentacaoEnum tipo;

    private BigDecimal valorVenda;

    private LocalDate dataVenda;

    @Column(nullable = false)
    private Long quantidade;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

}
