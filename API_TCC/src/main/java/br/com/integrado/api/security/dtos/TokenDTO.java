package br.com.integrado.api.security.dtos;

public class TokenDTO {

	private String token;
	
	public TokenDTO() {
		
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public TokenDTO(String token) {
		this.token = token;
	}
	

}
