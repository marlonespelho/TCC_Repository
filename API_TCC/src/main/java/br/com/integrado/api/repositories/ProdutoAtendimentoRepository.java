package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.ProdutoAtendiementoModel;

public interface ProdutoAtendimentoRepository extends JpaRepository<ProdutoAtendiementoModel, Long>{

}
