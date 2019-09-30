package br.com.integrado.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.integrado.api.entities.ProdutoAtendimentoModel;

public interface ProdutoAtendimentoRepository extends JpaRepository<ProdutoAtendimentoModel, Long>{

	Page<ProdutoAtendimentoModel> findByAtendimentoId(Pageable pageable, Long id);

	List<ProdutoAtendimentoModel> findByAtendimentoId(Long id);

	@Modifying
	@Query("DELETE FROM ProdutoAtendimentoModel pa WHERE pa.atendimento.id = :id")
	void deleteByAtendimentoId(@Param("id") Long id);

}
