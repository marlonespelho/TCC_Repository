package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;



public class FuncionarioDTO {
	
	private Long id;
	@NotEmpty
	private String nome;
	@CPF
	@NotEmpty
	private String cpf;
	@NotEmpty
	private String usuario;
	@NotEmpty
	private String senha;
	private Boolean inAtivo;
	private int perfil;
	
	public FuncionarioDTO() {	
	}

	public FuncionarioDTO(Long id, String nome, String cpf, String usuario, String senha, Boolean inAtivo,
			int perfil) {
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.usuario = usuario;
		this.senha = senha;
		this.inAtivo = inAtivo;
		this.perfil = perfil;
	}
	
	public FuncionarioDTO(String nome, String cpf, String usuario, String senha, Boolean inAtivo,
			int perfil) {
		this.nome = nome;
		this.cpf = cpf;
		this.usuario = usuario;
		this.senha = senha;
		this.inAtivo = inAtivo;
		this.perfil = perfil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getInAtivo() {
		return inAtivo;
	}

	public void setInAtivo(Boolean inAtivo) {
		this.inAtivo = inAtivo;
	}

	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

}
