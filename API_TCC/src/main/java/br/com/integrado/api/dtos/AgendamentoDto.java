package br.com.integrado.api.dtos;

import br.com.integrado.api.enums.StatusAgendamentoEnum;

public class AgendamentoDto {
	private Long id;
	private Long agenda;
	private Long cliente;
	private String horaInicio;
	private String horaFim;
	private StatusAgendamentoEnum status;
	
	public AgendamentoDto() {
	}
	
	public AgendamentoDto(Long agenda, Long cliente, String horaInicio, String horaFim,
			StatusAgendamentoEnum status) {
		this.agenda = agenda;
		this.cliente = cliente;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.status = status;
	}
	
	public AgendamentoDto(Long id, Long agenda, Long cliente, String horaInicio, String horaFim,
			StatusAgendamentoEnum status) {
		this.id = id;
		this.agenda = agenda;
		this.cliente = cliente;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.status = status;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAgenda() {
		return agenda;
	}
	public void setAgenda(Long agenda) {
		this.agenda = agenda;
	}
	public Long getCliente() {
		return cliente;
	}
	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
	public StatusAgendamentoEnum getStatus() {
		return status;
	}
	public void setStatus(StatusAgendamentoEnum status) {
		this.status = status;
	}
	
	
}
