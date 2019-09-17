package br.com.integrado.api.dtos;

import javax.validation.constraints.Email;
public class ContatoDTO {
	private Long id;
	private String numTelefone;
	@Email(message = "Email Inválido")
	private String email;
	private Long idPessoa;
	
	public ContatoDTO() {
	}
	
	public ContatoDTO(String numTelefone, @Email(message = "Email Inválido") String email, Long idPessoa) {
		this.numTelefone = numTelefone;
		this.email = email;
		this.idPessoa = idPessoa;
	}
	
	public ContatoDTO(Long id, String numTelefone, @Email(message = "Email Inválido") String email, Long idPessoa) {
		this.id = id;
		this.numTelefone = numTelefone;
		this.email = email;
		this.idPessoa = idPessoa;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumTelefone() {
		return numTelefone;
	}
	public void setNumTelefone(String numTelefone) {
		this.numTelefone = numTelefone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getIdPessoa() {
		return idPessoa;
	}
	public void setIdPessoa(Long idPessoa) {
		this.idPessoa = idPessoa;
	}
	
}
