package br.com.integrado.api.dtos;

public class ServicosAtendimentoDTO {

	private Long id;
	private Long servicoId;
	private Integer quantidade;
	private Double val_servico;
	private Long brindeGranho;
	private Long atendimentoId;
	
	public ServicosAtendimentoDTO(Long servicoId, Integer quantidade) {
		this.servicoId = servicoId;
		this.quantidade = quantidade;
	}
	
	public ServicosAtendimentoDTO(Long id, Long servicoId, Integer quantidade) {
		this.id = id;
		this.servicoId = servicoId;
		this.quantidade = quantidade;
	}
	
	public ServicosAtendimentoDTO(Long servicoId, Integer quantidade, Double val_servico, Long brindeGranho,
			Long atendimentoId) {
		this.servicoId = servicoId;
		this.quantidade = quantidade;
		this.val_servico = val_servico;
		this.brindeGranho = brindeGranho;
		this.atendimentoId = atendimentoId;
	}
	
	public ServicosAtendimentoDTO(Long id, Long servicoId, Integer quantidade, Double val_servico, Long brindeGranho,
			Long atendimentoId) {
		this.id = id;
		this.servicoId = servicoId;
		this.quantidade = quantidade;
		this.val_servico = val_servico;
		this.brindeGranho = brindeGranho;
		this.atendimentoId = atendimentoId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getServicoId() {
		return servicoId;
	}
	public void setServicoId(Long servicoId) {
		this.servicoId = servicoId;
	}
	public Integer getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}
	public Double getVal_servico() {
		return val_servico;
	}
	public void setVal_servico(Double val_servico) {
		this.val_servico = val_servico;
	}
	public Long getBrindeGranho() {
		return brindeGranho;
	}
	public void setBrindeGranho(Long brindeGranho) {
		this.brindeGranho = brindeGranho;
	}
	public Long getAtendimentoId() {
		return atendimentoId;
	}
	public void setAtendimentoId(Long atendimentoId) {
		this.atendimentoId = atendimentoId;
	}
		
}
