package br.com.integrado.api.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.integrado.api.entities.AgendaModel;
import br.com.integrado.api.entities.AgendamentoModel;
import br.com.integrado.api.enums.StatusAgendamento;
import br.com.integrado.api.repositories.AgendaRepository;

@Service
public class AgendaService {

	@Autowired
	private AgendaRepository agendaRepository;
	@Autowired
	private AgendamentosService agendamentosService;
	
	public AgendaModel criarAgenda(AgendaModel agenda) {
		agenda = this.agendaRepository.save(agenda);
		agendamentosService.criarAgendamentos(agenda);
		return agenda;
	}
	
	public Optional<AgendaModel> buscarPorId(Long id) {
		return this.agendaRepository.findById(id);
	}
	
	public Page<AgendaModel> buscarPorFuncionarioId(PageRequest pageRequest, Long id){
		return this.agendaRepository.findByFuncionarioId(pageRequest, id);
	}
	
	public Page<AgendaModel> buscarPorData(PageRequest pageRequest, Date data){
		return this.agendaRepository.findByData(pageRequest, data);
	}
	
	public Page<AgendaModel> buscarTodos(PageRequest pageRequest){
		return this.agendaRepository.findAll(pageRequest);
	}
	
	public Optional<AgendaModel> buscarPorData(Date data, Long id){
		return this.agendaRepository.findByDataAndFuncionarioId(data, id);
	}

	public void deletar(Long id) {
		this.agendamentosService.deletarPelaAgenda(this.buscarPorId(id).get());
		this.agendaRepository.deleteById(id);
	}
	
	public Boolean verificarAgendamentosMarcados(Long id) {
		Optional<AgendaModel> agenda = this.buscarPorId(id);
		if (!agenda.isPresent()) {
			return false;
		}
		List<AgendamentoModel> agendamentos = this.agendamentosService.buscarPorAgenda(agenda.get());
		for (AgendamentoModel agendamento : agendamentos) {
			if (agendamento.getStatus() != StatusAgendamento.ABERTO) {
				return false;
			}
		}
		return true;
	}
	
}
