package br.com.integrado.api.dtos;

public class AgendamentoDTO {
	private Long id;
	private Long agenda;
	private Long cliente;
	private String horaInicio;
	private String horaFim;
	private Long idAtendimento;
	private int status;
	
	public AgendamentoDTO() {
	}
	
	public AgendamentoDTO(Long agenda, Long cliente, String horaInicio, String horaFim,
			int status) {
		this.agenda = agenda;
		this.cliente = cliente;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.status = status;
	}
	
	public AgendamentoDTO(Long id, Long agenda, Long cliente, String horaInicio, String horaFim,
			int status) {
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public Long getIdAtendimento() {
		return idAtendimento;
	}

	public void setIdAtendimento(Long idAtendimento) {
		this.idAtendimento = idAtendimento;
	}

}
