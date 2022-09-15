package com.br.controleestoqueapi.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LucroProdutoDTO {
    private ProdutoDTO produto;
    private LucroDTO lucro;
}
