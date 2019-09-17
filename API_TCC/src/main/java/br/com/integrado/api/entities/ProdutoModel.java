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
@Table(name = "produto")
public class ProdutoModel implements Serializable{

	private static final long serialVersionUID = -3486062008480397306L;

	private Long id;
	private String descricao;
	private Integer quantidade;
	private	Integer quantMin;
	private Double valVendido;
	private MarcaModel marca;
	private String codBarras;
	private Boolean inAtivo;

	public ProdutoModel() {
	}
	
	public ProdutoModel(String descricao, Integer quantidade, Integer quantMin, Double valVendido, MarcaModel marca,
			String codBarras, Boolean inAtivo) {
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.quantMin = quantMin;
		this.valVendido = valVendido;
		this.marca = marca;
		this.codBarras = codBarras;
		this.inAtivo = inAtivo;
	}
	
	public ProdutoModel(Long id, String descricao, Integer quantidade, Integer quantMin, Double valVendido,
			MarcaModel marca, String codBarras, Boolean inAtivo) {
		this.id = id;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.quantMin = quantMin;
		this.valVendido = valVendido;
		this.marca = marca;
		this.codBarras = codBarras;
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
	
	@Column(nullable = true)
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	@Column(name = "quant_min")
	public Integer getQuantMin() {
		return quantMin;
	}
	public void setQuantMin(Integer quantMin) {
		this.quantMin = quantMin;
	}

	@Column(nullable = false)
	public Double getValVendido() {
		return valVendido;
	}
	public void setValVendido(Double valVendido) {
		this.valVendido = valVendido;
	}

	@ManyToOne
	@JoinColumn(name = "id_marca")
	public MarcaModel getMarca() {
		return marca;
	}
	public void setMarca(MarcaModel marca) {
		this.marca = marca;
	}

	@Column(name = "cod_barras", nullable = true, unique = true)
	public String getCodBarras() {
		return codBarras;
	}
	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}
	
	@Column(name = "in_ativo", nullable = false)
	public Boolean getInAtivo() {
		return inAtivo;
	}

	public void setInAtivo(Boolean inAtivo) {
		this.inAtivo = inAtivo;
	}

	@Override
	public String toString() {
		return "ProdutoModel [id=" + id + ", descricao=" + descricao + ", quantidade=" + quantidade + ", quantMin="
				+ quantMin + ", valVendido=" + valVendido + ", marca=" + marca + ", codBarras=" + codBarras + "]";
	}
	
}
