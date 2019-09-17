package br.com.integrado.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "prod_atendimento")
public class ProdutoAtendiementoModel implements Serializable{

	private static final long serialVersionUID = 1802385557789242447L;

	private Long id;
	private AtendimentoModel atendimento;
	private ProdutoModel produto;
	private Integer quantidade;
	private Double valUnitario;
	
	public ProdutoAtendiementoModel() {
	}

	public ProdutoAtendiementoModel(AtendimentoModel atendimento, ProdutoModel produto, Integer quantidade,
			Double valUnitario) {
		this.atendimento = atendimento;
		this.produto = produto;
		this.quantidade = quantidade;
		this.valUnitario = valUnitario;
	}

	public ProdutoAtendiementoModel(Long id, AtendimentoModel atendimento, ProdutoModel produto, Integer quantidade,
			Double valUnitario) {
		this.id = id;
		this.atendimento = atendimento;
		this.produto = produto;
		this.quantidade = quantidade;
		this.valUnitario = valUnitario;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "id_atendimento")
	public AtendimentoModel getAtendimento() {
		return atendimento;
	}
	public void setAtendimento(AtendimentoModel atendimento) {
		this.atendimento = atendimento;
	}

	@ManyToOne
	@JoinColumn(name = "id_produto")
	public ProdutoModel getProduto() {
		return produto;
	}
	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}

	@Column(nullable = false)
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Column(nullable = false)
	public Double getValUnitario() {
		return valUnitario;
	}
	public void setValUnitario(Double valUnitario) {
		this.valUnitario = valUnitario;
	}

	@Override
	public String toString() {
		return "ProdutoAtendiementoModel [id=" + id + ", atendimento=" + atendimento + ", produto=" + produto
				+ ", quantidade=" + quantidade + ", valUnitario=" + valUnitario + "]";
	}
	
}
