package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

public class CidadeDTO {
	private Integer id;
	@NotEmpty
	private String nome;
	private Integer idUF;
	
	public CidadeDTO() {
	}
	
	public CidadeDTO(String nome, Integer idUF) {
		this.nome = nome;
		this.idUF = idUF;
	}
	
	public CidadeDTO(Integer id, String nome, Integer idUF) {
		this.id = id;
		this.nome = nome;
		this.idUF = idUF;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getIdUF() {
		return idUF;
	}
	public void setIdUF(Integer idUF) {
		this.idUF = idUF;
	}
	
}
