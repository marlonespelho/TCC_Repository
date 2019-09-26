package br.com.integrado.api.dtos;

public class ProdutosAtendimentoDTO {

	private Long id;
	private Long produtoId;
	private Integer quantidade;
	private Double valUnitario;
	
	
	public ProdutosAtendimentoDTO(Long produtoId, Integer quantidade, Double valUnitario) {
		this.produtoId = produtoId;
		this.quantidade = quantidade;
		this.valUnitario = valUnitario;
	}
	
	public ProdutosAtendimentoDTO(Long id, Long produtoId, Integer quantidade, Double valUnitario) {
		this.id = id;
		this.produtoId = produtoId;
		this.quantidade = quantidade;
		this.valUnitario = valUnitario;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProdutoId() {
		return produtoId;
	}
	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Double getValUnitario() {
		return valUnitario;
	}
	public void setValUnitario(Double valUnitario) {
		this.valUnitario = valUnitario;
	}
	
}
