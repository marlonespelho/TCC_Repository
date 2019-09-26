package br.com.integrado.api.dtos;

public class BrindeConfigDTO {
	private Long id;
	private Integer quantContador;
	private Boolean brindeAniversario;
	private Boolean brindeFidelidade;
	
	public BrindeConfigDTO() {
	}
	
	public BrindeConfigDTO(Integer quantContador, Boolean brindeAniversario, Boolean brindeFidelidade) {
		this.quantContador = quantContador;
		this.brindeAniversario = brindeAniversario;
		this.brindeFidelidade = brindeFidelidade;
	}
	
	public BrindeConfigDTO(Long id, Integer quantContador, Boolean brindeAniversario, Boolean brindeFidelidade) {
		this.id = id;
		this.quantContador = quantContador;
		this.brindeAniversario = brindeAniversario;
		this.brindeFidelidade = brindeFidelidade;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getQuantContador() {
		return quantContador;
	}
	public void setQuantContador(Integer quantContador) {
		this.quantContador = quantContador;
	}
	public Boolean getBrindeAniversario() {
		return brindeAniversario;
	}
	public void setBrindeAniversario(Boolean brindeAniversario) {
		this.brindeAniversario = brindeAniversario;
	}
	public Boolean getBrindeFidelidade() {
		return brindeFidelidade;
	}
	public void setBrindeFidelidade(Boolean brindeFidelidade) {
		this.brindeFidelidade = brindeFidelidade;
	}
}
