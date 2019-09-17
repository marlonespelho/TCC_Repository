package br.com.integrado.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="agenda")
public class AgendaModel implements Serializable{

	private static final long serialVersionUID = 5308058423781609057L;
	
	private Long id;
	private Date data;
	private Date horaInicial;
	private Date horaFinal;
	private FuncionarioModel funcionario;

	public AgendaModel() {
	}
	
	public AgendaModel(Date data, FuncionarioModel funcionario, Date horaInicial, Date horaFinal) {
		this.data = data;
		this.funcionario = funcionario;
		this.horaFinal = horaFinal;
		this.horaInicial = horaInicial;
	}
	
	public AgendaModel(Long id, Date data, FuncionarioModel funcionario, Date horaInicial, Date horaFinal) {
		this.id = id;
		this.data = data;
		this.funcionario = funcionario;
		this.horaFinal = horaFinal;
		this.horaInicial = horaInicial;
	}
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	@ManyToOne
	@JoinColumn(name = "id_funcionario")
	public FuncionarioModel getFuncionario() {
		return funcionario;
	}
	public void setFuncionario(FuncionarioModel funcionario) {
		this.funcionario = funcionario;
	} 	
	@Temporal(TemporalType.TIME)
	@Column(name = "hora_inicial", nullable = false)
	public Date getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(Date horaInicial) {
		this.horaInicial = horaInicial;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "hora_final", nullable = false)
	public Date getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(Date horaFinal) {
		this.horaFinal = horaFinal;
	}

	@Override
	public String toString() {
		return "AgendaModel [id=" + id + ", data=" + data + ", funcionario=" + funcionario + "]";
	}
	
	/*@EmbeddedId
	private PkAgenda agenda;
	@ManyToOne
	@JoinColumn(name="idFuncionario",insertable =false, updatable = false)
	private FuncionarioModel funcionario;*/

}
