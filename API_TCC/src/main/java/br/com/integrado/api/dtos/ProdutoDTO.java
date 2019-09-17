package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

public class ProdutoDTO {
	private Long id;
	@NotEmpty
	private String descricao;
	private Integer quantidade;
	private	Integer quantMin;
	private Double valVendido;
	private Long idMarca;
	private String codBarras;
	private Boolean inAtivo;
	
	public ProdutoDTO() {
	}
	
	public ProdutoDTO(Long id, String descricao, Integer quantidade, Integer quantMin, Double valVendido, Long idMarca,
			String codBarras, Boolean inAtivo) {
		this.id = id;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.quantMin = quantMin;
		this.valVendido = valVendido;
		this.idMarca = idMarca;
		this.codBarras = codBarras;
		this.inAtivo = inAtivo;
	}
	
	public ProdutoDTO(String descricao, Integer quantidade, Integer quantMin, Double valVendido, Long idMarca,
			String codBarras, Boolean inAtivo) {
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.quantMin = quantMin;
		this.valVendido = valVendido;
		this.idMarca = idMarca;
		this.codBarras = codBarras;
		this.inAtivo = inAtivo;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Integer getQuantMin() {
		return quantMin;
	}
	public void setQuantMin(Integer quantMin) {
		this.quantMin = quantMin;
	}
	public Double getValVendido() {
		return valVendido;
	}
	public void setValVendido(Double valVendido) {
		this.valVendido = valVendido;
	}
	public Long getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(Long idMarca) {
		this.idMarca = idMarca;
	}
	public String getCodBarras() {
		return codBarras;
	}
	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public Boolean getInAtivo() {
		return inAtivo;
	}

	public void setInAtivo(Boolean inAtivo) {
		this.inAtivo = inAtivo;
	}
	
}
