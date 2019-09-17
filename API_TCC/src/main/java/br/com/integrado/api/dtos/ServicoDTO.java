package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

public class ServicoDTO {
	private Long id;
	@NotEmpty
	private String descricao;
	private Double valor;
	private Boolean inAtivo;
	
	public ServicoDTO() {
	}
	
	public ServicoDTO(String descricao, Double valor, Boolean inAtivo) {
		this.descricao = descricao;
		this.valor = valor;
		this.inAtivo = inAtivo;
	}
	
	public ServicoDTO(Long id, String descricao, Double valor, Boolean inAtivo) {
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
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
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Boolean getInAtivo() {
		return inAtivo;
	}
	public void setInAtivo(Boolean inAtivo) {
		this.inAtivo = inAtivo;
	}
	
}
