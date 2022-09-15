package com.br.controleestoqueapi.repository;

import com.br.controleestoqueapi.domain.dto.LucroDTO;
import com.br.controleestoqueapi.domain.entity.MovimentoEstoque;
import com.br.controleestoqueapi.domain.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovimentoEstoqueRepository extends JpaRepository<MovimentoEstoque, Long>, JpaSpecificationExecutor<MovimentoEstoque> {
    Page<MovimentoEstoque> findAll(Specification<MovimentoEstoque> spec, Pageable pageable);

    @Query("SELECT new com.br.controleestoqueapi.domain.dto.LucroDTO(sum(mg.quantidade) , sum(mg.valorVenda - (mg.quantidade * p.valorFornecedor))) FROM MovimentoEstoque mg JOIN mg.produto p WHERE p = ?1 AND mg.tipo = 'SAIDA'")
    Optional<LucroDTO> lucroPorProduto(Produto produto);

    @Query("SELECT sum(mg.quantidade) FROM MovimentoEstoque mg JOIN mg.produto p WHERE p = ?1 AND mg.tipo = 'SAIDA'")
    Optional<Long> countQuantidadeSaidaByProduto(Produto produto);

    long countByProduto(Produto produto);
}
