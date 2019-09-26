package br.com.integrado.api.dtos;


import java.util.List;

import javax.swing.ListModel;
import javax.validation.constraints.NotEmpty;

public class AtendimentoDTO {
	private Long id;
	private Long clienteId;
	private Long tipoPagamentoId;
	private Long formaPagamentoId;
	private Double valDesconto;
	@NotEmpty
	private String dataAtendimento;
	private Integer situacao;
	private String dataFinalPago;
	private Double valTotal;
	private List<ServicosAtendimentoDTO> servicosAtendimentoDTO;
	private List<ProdutosAtendimentoDTO> produtosAtendimentoDTO;
	
	
	public AtendimentoDTO(Long id, Long clienteId, Long tipoPagamentoId, Long formaPagamentoId, Double valDesconto,
			@NotEmpty String dataAtendimento,
			List<ServicosAtendimentoDTO> servicosAtendimentoDTO, List<ProdutosAtendimentoDTO> produtosAtendimentoDTO) {
		this.id = id;
		this.clienteId = clienteId;
		this.tipoPagamentoId = tipoPagamentoId;
		this.formaPagamentoId = formaPagamentoId;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.servicosAtendimentoDTO = servicosAtendimentoDTO;
		this.produtosAtendimentoDTO = produtosAtendimentoDTO;
	}
	
	public AtendimentoDTO(Long clienteId, Long tipoPagamentoId, Long formaPagamentoId, Double valDesconto,
			@NotEmpty String dataAtendimento,
			List<ServicosAtendimentoDTO> servicosAtendimentoDTO, List<ProdutosAtendimentoDTO> produtosAtendimentoDTO) {
		this.clienteId = clienteId;
		this.tipoPagamentoId = tipoPagamentoId;
		this.formaPagamentoId = formaPagamentoId;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.servicosAtendimentoDTO = servicosAtendimentoDTO;
		this.produtosAtendimentoDTO = produtosAtendimentoDTO;
	}
	
	public AtendimentoDTO(Long id, Long clienteId, Long tipoPagamentoId, Long formaPagamentoId, Double valDesconto,
			@NotEmpty String dataAtendimento, Integer situacao, String dataFinalPago, Double valTotal,
			List<ServicosAtendimentoDTO> servicosAtendimentoDTO, List<ProdutosAtendimentoDTO> produtosAtendimentoDTO) {
		this.id = id;
		this.clienteId = clienteId;
		this.tipoPagamentoId = tipoPagamentoId;
		this.formaPagamentoId = formaPagamentoId;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.situacao = situacao;
		this.dataFinalPago = dataFinalPago;
		this.valTotal = valTotal;
		this.servicosAtendimentoDTO = servicosAtendimentoDTO;
		this.produtosAtendimentoDTO = produtosAtendimentoDTO;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public Double getValDesconto() {
		return valDesconto;
	}
	public void setValDesconto(Double valDesconto) {
		this.valDesconto = valDesconto;
	}
	public String getDataAtendimento() {
		return dataAtendimento;
	}
	public void setDataAtendimento(String dataAtendimento) {
		this.dataAtendimento = dataAtendimento;
	}
	public Integer getSituacao() {
		return situacao;
	}
	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}
	public String getDataFinalPago() {
		return dataFinalPago;
	}
	public void setDataFinalPago(String dataFinalPago) {
		this.dataFinalPago = dataFinalPago;
	}
	public Double getValTotal() {
		return valTotal;
	}
	public void setValTotal(Double valTotal) {
		this.valTotal = valTotal;
	}
	public List<ServicosAtendimentoDTO> getServicosAtendimentoDTO() {
		return servicosAtendimentoDTO;
	}
	public void setServicosAtendimentoDTO(List<ServicosAtendimentoDTO> servicosAtendimentoDTO) {
		this.servicosAtendimentoDTO = servicosAtendimentoDTO;
	}
	public List<ProdutosAtendimentoDTO> getProdutosAtendimentoDTO() {
		return produtosAtendimentoDTO;
	}
	public void setProdutosAtendimentoDTO(List<ProdutosAtendimentoDTO> produtosAtendimentoDTO) {
		this.produtosAtendimentoDTO = produtosAtendimentoDTO;
	}

	public Long getTipoPagamentoId() {
		return tipoPagamentoId;
	}

	public void setTipoPagamentoId(Long tipoPagamentoId) {
		this.tipoPagamentoId = tipoPagamentoId;
	}

	public Long getFormaPagamentoId() {
		return formaPagamentoId;
	}

	public void setFormaPagamentoId(Long formaPagamentoId) {
		this.formaPagamentoId = formaPagamentoId;
	}
}
