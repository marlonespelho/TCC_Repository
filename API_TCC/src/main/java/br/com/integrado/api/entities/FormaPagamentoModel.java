package br.com.integrado.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fm_pagamento")
public class FormaPagamentoModel implements Serializable{
	
	private static final long serialVersionUID = -2977591265718066912L;

	private Long id;
	private String descricao;
	private Integer qntParcelas;
	private Integer qntDias;
	private Boolean entrada;
	private Boolean inAtivo;
	
	public FormaPagamentoModel() {
	}
	public FormaPagamentoModel(String descricao, Integer qntParcelas, Integer qntDias, Boolean entrada, Boolean inAtivo) {
		this.descricao = descricao;
		this.qntParcelas = qntParcelas;
		this.qntDias = qntDias;
		this.entrada = entrada;
		this.inAtivo = inAtivo;
	}
	public FormaPagamentoModel(Long id, String descricao, Integer qntParcelas, Integer qntDias, Boolean entrada, Boolean inAtivo) {
		this.id = id;
		this.descricao = descricao;
		this.qntParcelas = qntParcelas;
		this.qntDias = qntDias;
		this.entrada = entrada;
		this.inAtivo = inAtivo;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false)
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Column(name = "qnt_parc", nullable = false)
	public Integer getQntParcelas() {
		return qntParcelas;
	}
	public void setQntParcelas(Integer qntParcelas) {
		this.qntParcelas = qntParcelas;
	}
	
	@Column(name = "qnt_dias", nullable = false)
	public Integer getQntDias() {
		return qntDias;
	}
	public void setQntDias(Integer qntDias) {
		this.qntDias = qntDias;
	}
	
	@Column
	public Boolean getEntrada() {
		return entrada;
	}
	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}
	
	@Column(name = "in_ativo",  nullable = false)
	public Boolean getInAtivo() {
		return inAtivo;
	}
	public void setInAtivo(Boolean inAtivo) {
		this.inAtivo = inAtivo;
	}
	@Override
	public String toString() {
		return "FormaPagamentoModel [id=" + id + ", descricao=" + descricao + ", qntParcelas=" + qntParcelas
				+ ", qntDias=" + qntDias + ", entrada=" + entrada + "]";
	}
	
}
