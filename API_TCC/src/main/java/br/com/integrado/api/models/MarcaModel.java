package br.com.integrado.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "marca")
public class MarcaModel implements Serializable{

	private static final long serialVersionUID = 3665953326871829945L;
	
	private Long id;
	private String descricao;
	
	public MarcaModel(Long id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	public MarcaModel(String descricao) {
		this.descricao = descricao;
	}
	public MarcaModel() {
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
	@Override
	public String toString() {
		return "MarcaModel [id=" + id + ", descricao=" + descricao + "]";
	}	
	
}
