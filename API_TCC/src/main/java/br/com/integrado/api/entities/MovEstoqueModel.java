package br.com.integrado.api.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.integrado.api.enums.TipoMovimentacao;
@Entity
@Table(name = "mov_estoque")
public class MovEstoqueModel implements Serializable{

	private static final long serialVersionUID = 1860181321146825141L;
	
	private Long id;
	private Date dtMovimentacao;
	private String descricao;
	private ProdutoAtendimentoModel produtoAtendimento;
	private Integer quantidade;
	private TipoMovimentacao tipoMovimentacao;
	private ProdutoModel produto;
	
	public MovEstoqueModel(Long id, ProdutoModel produto, Date dtMovimentacao, String descricao,
			Integer quantidade, TipoMovimentacao tipoMovimentacao) {
		this.id = id;
		this.produto = produto;
		this.dtMovimentacao = dtMovimentacao;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.tipoMovimentacao = tipoMovimentacao;
	}
	
	public MovEstoqueModel(ProdutoModel produto, Date dtMovimentacao, String descricao,
			Integer quantidade, TipoMovimentacao tipoMovimentacao) {
		this.produto = produto;
		this.dtMovimentacao = dtMovimentacao;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.tipoMovimentacao = tipoMovimentacao;
	}
	
	public MovEstoqueModel( Date dtMovimentacao, String descricao, ProdutoAtendimentoModel produtoAtendimento,
							TipoMovimentacao tipoMovimentacao) {
		this.dtMovimentacao = dtMovimentacao;
		this.descricao = descricao;
		this.produtoAtendimento = produtoAtendimento;
		this.tipoMovimentacao = tipoMovimentacao;
	}
	
	public MovEstoqueModel(Long id, Date dtMovimentacao, String descricao, ProdutoAtendimentoModel produtoAtendimento,
							TipoMovimentacao tipoMovimentacao) {
		this.id = id;
		this.dtMovimentacao = dtMovimentacao;
		this.descricao = descricao;
		this.produtoAtendimento = produtoAtendimento;
		this.tipoMovimentacao = tipoMovimentacao;
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
	
	@OneToOne
	@JoinColumn(name = "prod_atend_id", nullable = true)
	public ProdutoAtendimentoModel getProdutoAtendimento() {
		return produtoAtendimento;
	}
	public void setProdutoAtendimento(ProdutoAtendimentoModel produtoAtendimento) {
		this.produtoAtendimento = produtoAtendimento;
	}
	
	@Column(nullable = true)
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	
	@ManyToOne
	@JoinColumn(nullable = true)
	public ProdutoModel getProduto() {
		return produto;
	}

	public void setProduto(ProdutoModel produto) {
		this.produto = produto;
	}

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "tipo_movimentacao", nullable = false)
	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}
	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	@Override
	public String toString() {
		return "MovEstoqueModel [id=" + id + ", dtMovimentacao=" + dtMovimentacao + ", descricao=" + descricao
				+ ", produtoAtendimento=" + produtoAtendimento + ", quantidade=" + quantidade + ", tipoMovimentacao="
				+ tipoMovimentacao + ", produto=" + produto + "]";
	}	
	
}
