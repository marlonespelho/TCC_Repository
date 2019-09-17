package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

public class FormaPagamentoDTO {

	private Long id;
	@NotEmpty
	private String descricao;
	private Integer qntParcelas;
	private Integer qntDias;
	private Boolean entrada;
	private Boolean inAtivo;
	
	public FormaPagamentoDTO() {
	}
	
	public FormaPagamentoDTO(@NotEmpty String descricao, Integer qntParcelas, Integer qntDias, Boolean entrada,
			Boolean inAtivo) {
		this.descricao = descricao;
		this.qntParcelas = qntParcelas;
		this.qntDias = qntDias;
		this.entrada = entrada;
		this.inAtivo = inAtivo;
	}
	
	public FormaPagamentoDTO(Long id, @NotEmpty String descricao, Integer qntParcelas, Integer qntDias, Boolean entrada,
			Boolean inAtivo) {
		this.id = id;
		this.descricao = descricao;
		this.qntParcelas = qntParcelas;
		this.qntDias = qntDias;
		this.entrada = entrada;
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
	public Integer getQntParcelas() {
		return qntParcelas;
	}
	public void setQntParcelas(Integer qntParcelas) {
		this.qntParcelas = qntParcelas;
	}
	public Integer getQntDias() {
		return qntDias;
	}
	public void setQntDias(Integer qntDias) {
		this.qntDias = qntDias;
	}
	public Boolean getEntrada() {
		return entrada;
	}
	public void setEntrada(Boolean entrada) {
		this.entrada = entrada;
	}
	public Boolean getInAtivo() {
		return inAtivo;
	}
	public void setInAtivo(Boolean inAtivo) {
		this.inAtivo = inAtivo;
	}
		
}
