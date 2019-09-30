package br.com.integrado.api.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.integrado.api.entities.ServicoAtendimentoModel;

public interface ServicoAtendimentoRepository extends JpaRepository<ServicoAtendimentoModel, Long> {

	Page<ServicoAtendimentoModel> findByAtendimentoId(Pageable pageable, Long id);

	List<ServicoAtendimentoModel> findByAtendimentoId(Long id);

	@Modifying
	@Query("DELETE FROM ServicoAtendimentoModel sa WHERE sa.atendimento.id = :id")
	void deleteByAtendimentoId(@Param("id") Long id);
	
}