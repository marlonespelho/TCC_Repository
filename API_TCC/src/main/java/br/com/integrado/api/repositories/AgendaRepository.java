package br.com.integrado.api.repositories;

import java.util.Date;
import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.integrado.api.entities.AgendaModel;

@NamedQueries({
	@NamedQuery(name = "AgendaRepository.findByFuncionarioId",
			query = "SELECT a FROM AgendaModel a WHERE a.funcionario.id = :id"),
	@NamedQuery(name = "AgendaRepository.findByDataAndFuncionarioId",
	query = "SELECT a FROM AgendaModel a WHERE a.funcionario.id = :id and a.data = :data")})
public interface AgendaRepository extends JpaRepository<AgendaModel, Long>{

	Page<AgendaModel> findByFuncionarioId(Pageable pageable, @Param("id") Long id);

	Page<AgendaModel> findByData(Pageable pageable, Date data);

	Optional<AgendaModel> findByDataAndFuncionarioId(@Param("data")Date data,@Param("id") Long id);

}
