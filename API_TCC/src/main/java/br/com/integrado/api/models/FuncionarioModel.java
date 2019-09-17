package br.com.integrado.api.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "funcionario")
@PrimaryKeyJoinColumn(name = "id")
public class FuncionarioModel extends PessoaModel {

	private static final long serialVersionUID = 5575149722123731021L;
	private Boolean gerente;
	private String usuario;
	private String senha;
	
	public FuncionarioModel() {
	}
	
	public FuncionarioModel(Long id, String nome, Boolean inAtivo, Boolean gerente, String usuario, String senha) {
		super(id, nome, inAtivo);
		this.gerente = gerente;
		this.usuario = usuario;
		this.senha = senha;
	}

	public FuncionarioModel(String nome, Boolean inAtivo, Boolean gerente, String usuario, String senha) {
		super(nome, inAtivo);
		this.gerente = gerente;
		this.usuario = usuario;
		this.senha = senha;
	}

	@Column(nullable = false)
	public Boolean getGerente() {
		return gerente;
	}
	public void setGerente(Boolean gerente) {
		this.gerente = gerente;
	}
	
	@Column(nullable = false)
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Column(nullable = false)
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Override
	public String toString() {
		return "FuncionarioModel [gerente=" + gerente + ", usuario=" + usuario + ", senha=" + senha + "]";
	}
	
}
