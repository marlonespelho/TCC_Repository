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
	private Double valTotalProdutos;
	private Double valTotalServicos;
	private List<ServicosAtendimentoDTO> servicosAtendimentoDTO;
	private List<ProdutosAtendimentoDTO> produtosAtendimentoDTO;
	private List<AgendamentoDTO> agendamentoDtos;
	private Long funcionarioId;
	
	public AtendimentoDTO() {

	}

	public AtendimentoDTO(Long id, Long funcionarioId, Long clienteId, Long tipoPagamentoId, Long formaPagamentoId, Double valDesconto,
			String dataAtendimento, Integer situacao, Double valTotalProdutos, Double valTotalServicos,
			List<ServicosAtendimentoDTO> servicosAtendimentoDTO, 
			List<ProdutosAtendimentoDTO> produtosAtendimentoDTO) {
		this.id = id;
		this.funcionarioId = funcionarioId;
		this.clienteId = clienteId;
		this.tipoPagamentoId = tipoPagamentoId;
		this.formaPagamentoId = formaPagamentoId;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.situacao = situacao;
		this.valTotalProdutos = valTotalProdutos;
		this.valTotalServicos = valTotalServicos;
		this.servicosAtendimentoDTO = servicosAtendimentoDTO;
		this.produtosAtendimentoDTO = produtosAtendimentoDTO;
		
	}
	
	public AtendimentoDTO(Long funcionarioId, Long clienteId, Long tipoPagamentoId, Long formaPagamentoId, Double valDesconto,
			@NotEmpty String dataAtendimento, Integer situacao,
			List<ServicosAtendimentoDTO> servicosAtendimentoDTO, List<ProdutosAtendimentoDTO> produtosAtendimentoDTO) {
		this.funcionarioId = funcionarioId;
		this.clienteId = clienteId;
		this.tipoPagamentoId = tipoPagamentoId;
		this.formaPagamentoId = formaPagamentoId;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.situacao = situacao;
		this.servicosAtendimentoDTO = servicosAtendimentoDTO;
		this.produtosAtendimentoDTO = produtosAtendimentoDTO;
	}
	
	public AtendimentoDTO(Long id, Long funcionarioId, Long clienteId, Long tipoPagamentoId, Long formaPagamentoId, Double valDesconto,
			@NotEmpty String dataAtendimento, Integer situacao, String dataFinalPago, Double valTotalProdutos, Double valTotalServicos,
			List<ServicosAtendimentoDTO> servicosAtendimentoDTO, List<ProdutosAtendimentoDTO> produtosAtendimentoDTO) {
		this.id = id;
		this.funcionarioId = funcionarioId;
		this.clienteId = clienteId;
		this.tipoPagamentoId = tipoPagamentoId;
		this.formaPagamentoId = formaPagamentoId;
		this.valDesconto = valDesconto;
		this.dataAtendimento = dataAtendimento;
		this.situacao = situacao;
		this.dataFinalPago = dataFinalPago;
		this.valTotalProdutos = valTotalProdutos;
		this.valTotalServicos = valTotalServicos;
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
	
	public Double getValTotalProdutos() {
		return valTotalProdutos;
	}

	public void setValTotalProdutos(Double valTotalProdutos) {
		this.valTotalProdutos = valTotalProdutos;
	}

	public Double getValTotalServicos() {
		return valTotalServicos;
	}

	public void setValTotalServicos(Double valTotalServicos) {
		this.valTotalServicos = valTotalServicos;
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

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public List<AgendamentoDTO> getAgendamentoDtos() {
		return agendamentoDtos;
	}

	public void setAgendamentoDtos(List<AgendamentoDTO> agendamentoDtos) {
		this.agendamentoDtos = agendamentoDtos;
	}
	
	
}
