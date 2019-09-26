package br.com.integrado.api.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.dtos.AgendamentoDTO;
import br.com.integrado.api.entities.AgendaModel;
import br.com.integrado.api.entities.AgendamentoModel;
import br.com.integrado.api.enums.StatusAgendamento;
import br.com.integrado.api.repositories.AgendamentoRepository;
import br.com.integrado.api.utils.DataUtils;

@Service
public class AgendamentosService {
	@Autowired
	private AgendamentoRepository agendamentoRepository;
	
	public AgendamentoModel salvar(AgendamentoModel agendamento) {
		return this.agendamentoRepository.save(agendamento);
	}
	
	public void deletar(Long id) {
		this.agendamentoRepository.deleteById(id);
	}
	
	public Optional<AgendamentoModel> buscarPorId(Long id){
		return this.agendamentoRepository.findById(id);
	}
	
	public void criarAgendamentos(AgendaModel agenda) {
		int quantHorarios = (int) (agenda.getHoraFinal().getTime()/3600000 - agenda.getHoraInicial().getTime()/3600000) * 2;
		GregorianCalendar inicioHorario = new GregorianCalendar();
		inicioHorario.setTime(agenda.getHoraInicial());
		GregorianCalendar fimHorario = new GregorianCalendar();
		fimHorario.setTime(agenda.getHoraInicial());
		fimHorario.add(Calendar.MINUTE, 30);
		for (int i = 1; i <= quantHorarios; i++) {
			AgendamentoModel agendamento = new AgendamentoModel(agenda, inicioHorario.getTime(), 
					fimHorario.getTime(), StatusAgendamento.ABERTO);
			this.agendamentoRepository.save(agendamento);
			inicioHorario.add(Calendar.MINUTE, 30);
			fimHorario.add(Calendar.MINUTE, 30);
		}
	}
	
	public void deletarPelaAgenda(AgendaModel agenda) {
		this.agendamentoRepository.deleteByAgenda(agenda);
	}
	
	public Page<AgendamentoModel> buscarPorAgendaId(PageRequest pageRequest, Long id){
		return this.agendamentoRepository.findByAgendaId(pageRequest, id);
	}
	
	public Page<AgendamentoModel> buscarPorClienteId(PageRequest pageRequest, Long id){
		return this.agendamentoRepository.findByClienteId(pageRequest, id);
	}
	
	public Page<AgendamentoModel> buscarPorFuncionarioId(PageRequest pageRequest, Long id){
		return this.agendamentoRepository.findByAgendaFuncionarioId(pageRequest, id);
	}
	
	public Page<AgendamentoModel> buscarPorFuncionarioIdEData(PageRequest pageRequest, Long id, Date data){
		return this.agendamentoRepository.findByAgendaFuncionarioIdAndAgendaData(pageRequest, id, data);
	}
	
	public Page<AgendamentoModel> buscarPorStatusEFuncionarioId(PageRequest pageRequest, StatusAgendamento status, Long id){
		return this.agendamentoRepository.findByStatusAndAgendaFuncionarioId(pageRequest, status, id);
	}
	
	public Page<AgendamentoModel> buscarPorStatus(PageRequest pageRequest, StatusAgendamento status){
		return this.agendamentoRepository.findByStatus(pageRequest, status);
	}
	
	public List<AgendamentoModel> buscarPorAgenda(AgendaModel agenda){
		List<AgendamentoModel> agendamentos = this.agendamentoRepository.findByAgenda(agenda);
		return agendamentos;
	}

	public void deletarAgendamentosAbertos() {
		Date dataAtual = new DataUtils().zerarHoras(new Date());
		this.agendamentoRepository.deleteByAgendaDataAndStatus(dataAtual, StatusAgendamento.ABERTO);
	}

	public Page<AgendamentoModel> buscarTodos(PageRequest pageRequest) {
		return this.agendamentoRepository.findAll(pageRequest);
	}

	public Page<AgendamentoModel> buscarPorStatusEClienteId(PageRequest pageRequest,
			StatusAgendamento status, Long id) {
		return this.agendamentoRepository.findByStatusAndClienteId(pageRequest, status, id);
	}

}
