package br.com.integrado.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.integrado.api.enums.StatusAgendamentoEnum;
@Entity
@Table(name = "agendamento")
public class AgendamentoModel implements Serializable{

	private static final long serialVersionUID = -2741175583334260537L;

	private Long id;
	private AgendaModel agenda;
	private ClienteModel cliente;
	private Date horaInicio;
	private Date horaFim;
	private StatusAgendamentoEnum status;
	
	public AgendamentoModel() {
	}

	public AgendamentoModel(AgendaModel agenda, ClienteModel cliente, Date horaInicio, Date horaFim,
			StatusAgendamentoEnum status) {
		this.agenda = agenda;
		this.cliente = cliente;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.status = status;
	}

	public AgendamentoModel(AgendaModel agenda, Date horaInicio, Date horaFim,
			StatusAgendamentoEnum status) {
		this.agenda = agenda;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.status = status;
	}
	
	public AgendamentoModel(Long id, AgendaModel agenda, ClienteModel cliente, Date horaInicio, Date horaFim,
			StatusAgendamentoEnum status) {
		this.id = id;
		this.agenda = agenda;
		this.cliente = cliente;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.status = status;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "id_agenda")
	public AgendaModel getAgenda() {
		return agenda;
	}
	public void setAgenda(AgendaModel agenda) {
		this.agenda = agenda;
	}

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	public ClienteModel getCliente() {
		return cliente;
	}
	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}

	@Column(name = "hora_inicio", nullable = false)
	@Temporal(TemporalType.TIME)
	public Date getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	@Column(name = "hora_fim", nullable = false)
	@Temporal(TemporalType.TIME)
	public Date getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(Date horaFim) {
		this.horaFim = horaFim;
	}
	
	@Column()
	@Enumerated(EnumType.ORDINAL)
	public StatusAgendamentoEnum getStatus() {
		return status;
	}
	public void setStatus(StatusAgendamentoEnum status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "AgendamentoModel [id=" + id + ", agenda=" + agenda + ", cliente=" + cliente + ", horaInicio="
				+ horaInicio + ", horaFim=" + horaFim + ", status=" + status + ", atendimento=" + "]";
	}
	
}
