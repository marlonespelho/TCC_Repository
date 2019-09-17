package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

public class AgendaDTO {
	private Long id;
	@NotEmpty
	private String data;
	private Long funcionarioId;
	@NotEmpty
	private String horaInicial;
	@NotEmpty
	private String horaFinal;
	
	public AgendaDTO() {
	}
	
	public AgendaDTO(@NotEmpty String data, Long funcionarioId, @NotEmpty String horaInicial,
			@NotEmpty String horaFinal) {
		this.data = data;
		this.funcionarioId = funcionarioId;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
	}
	
	public AgendaDTO(Long id, @NotEmpty String data, Long funcionarioId, @NotEmpty String horaInicial,
			@NotEmpty String horaFinal) {
		this.id = id;
		this.data = data;
		this.funcionarioId = funcionarioId;
		this.horaInicial = horaInicial;
		this.horaFinal = horaFinal;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Long getFuncionarioId() {
		return funcionarioId;
	}
	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public String getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}
	
	
}
