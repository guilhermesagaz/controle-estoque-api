package com.br.controleestoqueapi.domain.entity;

import com.br.controleestoqueapi.domain.enums.TipoProdutoEnum;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_produto")
public class Produto extends GenericModel {

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoProdutoEnum tipo;

    @Column(nullable = false)
    private BigDecimal valorFornecedor;

    @Builder.Default
    private Long quantidadeEstoque = 0L;

}
