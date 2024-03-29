package br.com.integrado.api.repositories;

import java.util.Date;
import java.util.List;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.com.integrado.api.entities.AgendaModel;
import br.com.integrado.api.entities.AgendamentoModel;
import br.com.integrado.api.enums.StatusAgendamento;

@NamedQueries({
	@NamedQuery(name = "AgendamentoRepository.findByAgendaId",
		query = "SELECT ag FROM AgendamentoModel ag WHERE ag.agenda.id = :id"),
	@NamedQuery(name = "AgendamentoRepository.findByClienteId",
		query = "SELECT ag FROM AgendamentoModel ag WHERE ag.cliente.id = :id"),
	@NamedQuery(name = "AgendamentoRepository.findByAgendaFuncionarioId",
		query = "SELECT ag FROM AgendamentoModel ag WHERE ag.agenda.funcionario.id = :id"),
	@NamedQuery(name = "AgendamentoRepository.findByAgendaFuncionarioIdAndAgendaData",
		query = "SELECT ag FROM AgendamentoModel ag WHERE ag.agenda.funcionario.id = :id AND ag.agenda.data = :data"),
	@NamedQuery(name = "AgendamentoRepository.findByStatusAndAgendaFuncionarioId",
		query = "SELECT ag FROM AgendamentoModel ag WHERE ag.agenda.funcionario.id = :id AND ag.status = :status"),
	@NamedQuery(name = "AgendamentoRepository.findByStatusAndClienteId",
		query = "SELECT ag FROM AgendamentoModel ag WHERE ag.cliente.id = :id AND ag.status = :status")
})
public interface AgendamentoRepository extends JpaRepository<AgendamentoModel, Long>{

	List<AgendamentoModel> findByAgenda(AgendaModel agenda);

	void deleteByAgenda(AgendaModel agenda);

	Page<AgendamentoModel> findByAgendaId(Pageable pageable, @Param("id") Long id);

	Page<AgendamentoModel> findByClienteId(Pageable pageable, @Param("id") Long id);

	Page<AgendamentoModel> findByAgendaFuncionarioId(Pageable pageable, @Param("id") Long id);

	Page<AgendamentoModel> findByAgendaFuncionarioIdAndAgendaData(Pageable pageable, @Param("id") Long id, @Param("data") Date data);

	Page<AgendamentoModel> findByStatusAndAgendaFuncionarioId(Pageable pageable, @Param("status") StatusAgendamento status,
																@Param("id") Long id);

	Page<AgendamentoModel> findByStatus(Pageable pageable, StatusAgendamento status);

	@Modifying
	@Query("DELETE FROM AgendamentoModel ag WHERE ag.agenda.data = :data AND ag.status = :status")
	void deleteByAgendaDataAndStatus(@Param("data") Date dataAtual, @Param("status") StatusAgendamento status);

	Page<AgendamentoModel> findByStatusAndClienteId(Pageable pageable, StatusAgendamento status, Long id);

}
