package br.com.integrado.api.dtos;

public class ServicosAtendimentoDTO {

	private Long id;
	private Long servicoId;
	private Integer quantidade;
	private Double val_servico;
	private Boolean brindeAniversario;
	private Long brindeConfig;
	private Long atendimentoId;
	
	public ServicosAtendimentoDTO(Long servicoId, Integer quantidade, Boolean brindeAniversario, Long brindeConfig) {
		this.servicoId = servicoId;
		this.quantidade = quantidade;
		this.brindeAniversario = brindeAniversario;
		this.brindeConfig = brindeConfig;
	}
	
	public ServicosAtendimentoDTO(Long id, Long servicoId, Integer quantidade, Long atendimentoId) {
		this.id = id;
		this.servicoId = servicoId;
		this.quantidade = quantidade;
		this.atendimentoId = atendimentoId;
	}
	
	public ServicosAtendimentoDTO(Long id, Long servicoId, Integer quantidade, Double val_servico,
			Boolean brindeAniversario, Long brindeConfig, Long atendimentoId) {
		this.id = id;
		this.servicoId = servicoId;
		this.quantidade = quantidade;
		this.val_servico = val_servico;
		this.brindeAniversario = brindeAniversario;
		this.brindeConfig = brindeConfig;
		this.atendimentoId = atendimentoId;
	}

	public ServicosAtendimentoDTO() {
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
	public Long getAtendimentoId() {
		return atendimentoId;
	}
	public void setAtendimentoId(Long atendimentoId) {
		this.atendimentoId = atendimentoId;
	}

	public Boolean getBrindeAniversario() {
		return brindeAniversario;
	}

	public void setBrindeAniversario(Boolean brindeAniversario) {
		this.brindeAniversario = brindeAniversario;
	}

	public Long getBrindeConfig() {
		return brindeConfig;
	}

	public void setBrindeConfig(Long brindeConfig) {
		this.brindeConfig = brindeConfig;
	}	
	
	
}
