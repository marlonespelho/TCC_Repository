package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.ProdutoModel;

public interface ProdutoRepository extends JpaRepository<ProdutoModel, Long> {
	
}
