package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

public class TipoPagamentoDTO {
	private Long id;
	@NotEmpty
	private String descricao;
	private Boolean inAtivo;
	
	public TipoPagamentoDTO() {
	}
	
	public TipoPagamentoDTO(String descricao, Boolean inAtivo) {
		this.descricao = descricao;
		this.inAtivo = inAtivo;
	}
	
	public TipoPagamentoDTO(Long id, String descricao, Boolean inAtivo) {
		this.id = id;
		this.descricao = descricao;
		this.inAtivo = inAtivo;
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
