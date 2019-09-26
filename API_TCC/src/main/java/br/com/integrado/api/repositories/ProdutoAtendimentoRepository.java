package br.com.integrado.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.integrado.api.entities.ProdutoAtendimentoModel;

public interface ProdutoAtendimentoRepository extends JpaRepository<ProdutoAtendimentoModel, Long>{

	Page<ProdutoAtendimentoModel> findByAtendimentoId(Pageable pageable, Long id);

}
