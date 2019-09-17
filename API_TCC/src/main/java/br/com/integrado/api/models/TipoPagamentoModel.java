package br.com.integrado.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tp_pagamento")
public class TipoPagamentoModel implements Serializable{
	private static final long serialVersionUID = 2536271916530699733L;

	private Long id;
	private String descricao;
	
	public TipoPagamentoModel() {
	}
	
	public TipoPagamentoModel(String descricao) {
		this.descricao = descricao;
	}
	
	public TipoPagamentoModel(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public String toString() {
		return "TipoPagamentoModel [id=" + id + ", descricao=" + descricao + "]";
	}
	
	
}
