package br.com.integrado.api.security.dtos;

import javax.validation.constraints.NotEmpty;

public class JwtAuthenticationDTO {
	
	@NotEmpty
	private String usuario;
	@NotEmpty
	private String senha;

	public JwtAuthenticationDTO() {
		
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

	
	
}
