package br.com.integrado.api.models;

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
	
	public ProdutoModel() {
	}
	
	public ProdutoModel(String descricao, Integer quantidade, Integer quantMin, Double valVendido, MarcaModel marca,
			String codBarras) {
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.quantMin = quantMin;
		this.valVendido = valVendido;
		this.marca = marca;
		this.codBarras = codBarras;
	}
	
	public ProdutoModel(Long id, String descricao, Integer quantidade, Integer quantMin, Double valVendido,
			MarcaModel marca, String codBarras) {
		this.id = id;
		this.descricao = descricao;
		this.quantidade = quantidade;
		this.quantMin = quantMin;
		this.valVendido = valVendido;
		this.marca = marca;
		this.codBarras = codBarras;
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

	@Column(name = "cod_barras", nullable = true)
	public String getCodBarras() {
		return codBarras;
	}
	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	@Override
	public String toString() {
		return "ProdutoModel [id=" + id + ", descricao=" + descricao + ", quantidade=" + quantidade + ", quantMin="
				+ quantMin + ", valVendido=" + valVendido + ", marca=" + marca + ", codBarras=" + codBarras + "]";
	}
	
}
