package br.com.integrado.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.models.ProdutoAtendiementoModel;

public interface ProdutoAtendimentoRepository extends JpaRepository<ProdutoAtendiementoModel, Long>{

}
