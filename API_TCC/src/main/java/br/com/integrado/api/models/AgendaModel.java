package br.com.integrado.api.models;

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
	private FuncionarioModel funcionario;

	public AgendaModel() {
	}
	public AgendaModel(Date data, FuncionarioModel funcionario) {
		super();
		this.data = data;
		this.funcionario = funcionario;
	}
	public AgendaModel(Long id, Date data, FuncionarioModel funcionario) {
		super();
		this.id = id;
		this.data = data;
		this.funcionario = funcionario;
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
