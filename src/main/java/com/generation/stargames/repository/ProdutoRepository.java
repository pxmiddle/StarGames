package com.generation.stargames.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.generation.stargames.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	List<Produto> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
	List<Produto> findByPrecoLessThan(@Param("menor")BigDecimal preco);
	List<Produto> findByPrecoGreaterThan(@Param("maior")BigDecimal preco);
	
}
