package br.com.integrado.api.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "mov_estoque")
public class MovEstoqueModel implements Serializable{

	private static final long serialVersionUID = 1860181321146825141L;
	
	private Long id;
	private Date dtMovimentacao;
	private String descricao;
	
	public MovEstoqueModel(Long id, Date dtMovimentacao, String descricao) {
		this.id = id;
		this.dtMovimentacao = dtMovimentacao;
		this.descricao = descricao;
	}
	public MovEstoqueModel(Date dtMovimentacao, String descricao) {
		this.dtMovimentacao = dtMovimentacao;
		this.descricao = descricao;
	}
	public MovEstoqueModel() {
	}
	
	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "dt_alteracao")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDtMovimentacao() {
		return dtMovimentacao;
	}
	public void setDtMovimentacao(Date dtMovimentacao) {
		this.dtMovimentacao = dtMovimentacao;
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
		return "MovEstoqueModel [id=" + id + ", dtMovimentacao=" + dtMovimentacao + "]";
	}
	
}
