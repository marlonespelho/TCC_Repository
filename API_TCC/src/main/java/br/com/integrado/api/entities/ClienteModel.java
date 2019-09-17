package br.com.integrado.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cliente")
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class ClienteModel extends PessoaModel{

	private static final long serialVersionUID = -4774549427639190801L;
	private Date dtNascimento;
	private CPFModel cpf;
		
	public ClienteModel() {
		super();
	}

	public ClienteModel(Long id, String nome, Boolean inAtivo, Date dtNascimento, CPFModel cpf) {
		super(id, nome, inAtivo);
		this.dtNascimento = dtNascimento;
		this.cpf = cpf;
	}

	public ClienteModel(String nome, Boolean inAtivo, Date dtNascimento, CPFModel cpf) {
		super(nome, inAtivo);
		this.dtNascimento = dtNascimento;
		this.cpf = cpf;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_nascimento", nullable = false)
	public Date getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}
	
	@Embedded
	@Column(nullable = false, unique = true)
	public CPFModel getCpf() {
		return cpf;
	}
	public void setCpf(CPFModel cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "ClienteModel [dtNascimento=" + dtNascimento + ", cpf=" + cpf.toString() + "]";
	}

}
