package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

public class MarcaDTO {
	private Long id;
	@NotEmpty(message = "descricao não pode ser vazia")
	private String descricao;
	private Boolean inAtivo;
	
	public MarcaDTO(Long id, String descricao, Boolean inAtivo) {
		this.id = id;
		this.descricao = descricao;
		this.inAtivo = inAtivo;
	}
	
	public MarcaDTO(String descricao, Boolean inAtivo) {
		this.descricao = descricao;
		this.inAtivo = inAtivo;
	}
	
	public MarcaDTO() {
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getInAtivo() {
		return inAtivo;
	}

	public void setInAtivo(Boolean inAtivo) {
		this.inAtivo = inAtivo;
	}
	
}
