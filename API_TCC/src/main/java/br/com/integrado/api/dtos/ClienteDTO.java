package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

public class ClienteDTO {
	private Long id;
	@NotEmpty(message = "O nome não pode estar vazio.")
	private String nome;
	private Boolean inAtivo;
	@NotEmpty
	private String dtNascimento;
	@NotEmpty
	@CPF(message = "CPF inválido")
	private String cpf;
		
	public ClienteDTO() {
		
	}
	
	public ClienteDTO(Long id, String nome, Boolean inAtivo, String dtNascimento, String cpf) {
		this.id = id;
		this.nome = nome;
		this.inAtivo = inAtivo;
		this.dtNascimento = dtNascimento;
		this.cpf = cpf;
	}

	public ClienteDTO( String nome, Boolean inAtivo, String dtNascimento, String cpf) {
		this.nome = nome;
		this.inAtivo = inAtivo;
		this.dtNascimento = dtNascimento;
		this.cpf = cpf;
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
	public Boolean getInAtivo() {
		return inAtivo;
	}
	public void setInAtivo(Boolean inAtivo) {
		this.inAtivo = inAtivo;
	}
	public String getDtNascimento() {
		return dtNascimento;
	}
	public void setDtNascimento(String dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public String toString() {
		return "ClienteDTO [id=" + id + ", nome=" + nome + ", inAtivo=" + inAtivo + ", dtNascimento=" + dtNascimento
				+ ", cpf=" + cpf + "]";
	}
	
}
