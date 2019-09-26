package br.com.integrado.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_movimentacao")
public class ItensMovEstoqueModel implements Serializable{

	private static final long serialVersionUID = 608632514761069048L;

	private Long id;
	private ProdutoModel produto;
	private MovEstoqueModel movEstoque;
	private Integer quantidade;

	
	public ItensMovEstoqueModel() {
	}
	
	public ItensMovEstoqueModel(ProdutoModel produto, MovEstoqueModel movEstoque, Integer quantidade) {
		this.produto = produto;
		this.movEstoque = movEstoque;
		this.quantidade = quantidade;
	}
	
	public ItensMovEstoqueModel(Long id, ProdutoModel produto, MovEstoqueModel movEstoque, Integer quantidade) {
		this.id = id;
		this.produto = produto;
		this.movEstoque = movEstoque;
		this.quantidade = quantidade;
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
	@JoinColumn(name = "id_produto")
	public ProdutoModel getProduto() {
		return produto;
	}
	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_movimentacao")
	public MovEstoqueModel getMovEstoque() {
		return movEstoque;
	}
	public void setMovEstoque(MovEstoqueModel movEstoque) {
		this.movEstoque = movEstoque;
	}
	
	@Column(nullable = false)
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public String toString() {
		return "ItensMovEstoqueModel [id=" + id + ", produto=" + produto + ", movEstoque=" + movEstoque
				+ ", quantidade=" + quantidade + "]";
	}
	
}
