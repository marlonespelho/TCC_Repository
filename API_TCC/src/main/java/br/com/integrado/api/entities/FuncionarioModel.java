package br.com.integrado.api.entities;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import br.com.integrado.api.enums.PerfilUsuario;

@Entity
@Table(name = "funcionario")
@PrimaryKeyJoinColumn(name = "id")
public class FuncionarioModel extends PessoaModel {

	private static final long serialVersionUID = 5575149722123731021L;
	private PerfilUsuario perfil;
	private String usuario;
	private String senha;
	private CPFModel cpf;
	
	public FuncionarioModel() {
	}
	
	public FuncionarioModel(Long id, String nome, Boolean inAtivo, PerfilUsuario perfil, String usuario, String senha,CPFModel cpf) {
		super(id, nome, inAtivo);
		this.perfil = perfil;
		this.usuario = usuario;
		this.senha = senha;
		this.cpf = cpf;
	}

	public FuncionarioModel(String nome, Boolean inAtivo, PerfilUsuario perfil, String usuario, String senha, CPFModel cpf) {
		super(nome, inAtivo);
		this.perfil = perfil;
		this.usuario = usuario;
		this.senha = senha;
		this.cpf = cpf;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	public PerfilUsuario getPerfil() {
		return perfil;
	}
	public void setPerfil(PerfilUsuario perfil) {
		this.perfil = perfil;
	}
	
	@Column(nullable = false, unique = true)
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	@Column(nullable = false)
	public String getSenha() {
		return this.senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
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
		return "FuncionarioModel [gerente=" + perfil + ", usuario=" + usuario + ", senha=" + senha + "]";
	}
	
}
