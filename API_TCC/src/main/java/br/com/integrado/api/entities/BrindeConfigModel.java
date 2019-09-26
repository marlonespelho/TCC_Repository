package br.com.integrado.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "brinde_config")
public class BrindeConfigModel {

	private Long id;
	private Integer quantContador;
	private Boolean brindeAniversario;
	private Boolean brindeFidelidade;
	
	public BrindeConfigModel() {
	}
	
	public BrindeConfigModel(Integer quantContador, Boolean brindeAniversario, Boolean brindeFidelidade) {
		this.quantContador = quantContador;
		this.brindeAniversario = brindeAniversario;
		this.brindeFidelidade = brindeFidelidade;
	}
	
	public BrindeConfigModel(Long id, Integer quantContador, Boolean brindeAniversario, Boolean brindeFidelidade) {
		this.id = id;
		this.quantContador = quantContador;
		this.brindeAniversario = brindeAniversario;
		this.brindeFidelidade = brindeFidelidade;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "quant_contador", nullable = false)
	public Integer getQuantContador() {
		return quantContador;
	}
	public void setQuantContador(Integer quantContador) {
		this.quantContador = quantContador;
	}
	@Column(name = "brinde_aniversario", nullable = false)
	public Boolean getBrindeAniversario() {
		return brindeAniversario;
	}

	public void setBrindeAniversario(Boolean brindeAniversario) {
		this.brindeAniversario = brindeAniversario;
	}

	public Boolean getBrindeFidelidade() {
		return brindeFidelidade;
	}

	@Column(name = "brinde_fidelidade", nullable = false)
	public void setBrindeFidelidade(Boolean brindeFidelidade) {
		this.brindeFidelidade = brindeFidelidade;
	}
	
}
