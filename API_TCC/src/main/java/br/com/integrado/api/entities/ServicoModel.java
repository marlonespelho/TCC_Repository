package br.com.integrado.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "servico")
public class ServicoModel implements Serializable{

	private static final long serialVersionUID = -6594845871778264095L;

	private Long id;
	private String descricao;
	private Double valor;
	private Boolean inAtivo;
	
	public ServicoModel() {
		
	}

	public ServicoModel(Long id, String descricao, Double valor, Boolean inAtivo) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.valor = valor;
		this.inAtivo = inAtivo;
	}

	public ServicoModel(String descricao, Double valor, Boolean inAtivo) {
		super();
		this.descricao = descricao;
		this.valor = valor;
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
	
	@Column(nullable = false)
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Column(nullable = false)
	public Boolean getInAtivo() {
		return inAtivo;
	}
	public void setInAtivo(Boolean inAtivo) {
		this.inAtivo = inAtivo;
	}

	@Override
	public String toString() {
		return "ServicoModel [id=" + id + ", descricao=" + descricao + ", valor=" + valor + ", inAtivo=" + inAtivo
				+ "]";
	}
	
}
