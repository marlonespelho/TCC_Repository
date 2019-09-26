package br.com.integrado.api.dtos;

public class BrindeDTO {
	
	private Long id;
	private Long clienteId;
	private Long servicoId;
	private Integer contadorBrinde;
	private String dataBrindeAniversario;
	private Long brindeConfigId;
	
	public BrindeDTO(Long clienteId, Long servicoId, Integer contadorBrinde, Long brindeConfigId) {
		this.clienteId = clienteId;
		this.servicoId = servicoId;
		this.contadorBrinde = contadorBrinde;
		this.brindeConfigId = brindeConfigId;
	}
	
	public BrindeDTO(Long clienteId, Long servicoId, Integer contadorBrinde, String dataBrindeAniversario,
			Long brindeConfigId) {
		this.clienteId = clienteId;
		this.servicoId = servicoId;
		this.contadorBrinde = contadorBrinde;
		this.dataBrindeAniversario = dataBrindeAniversario;
		this.brindeConfigId = brindeConfigId;
	}
	
	public BrindeDTO(Long id, Long clienteId, Long servicoId, Integer contadorBrinde, String dataBrindeAniversario,
			Long brindeConfigId) {
		this.id = id;
		this.clienteId = clienteId;
		this.servicoId = servicoId;
		this.contadorBrinde = contadorBrinde;
		this.dataBrindeAniversario = dataBrindeAniversario;
		this.brindeConfigId = brindeConfigId;
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
	public Long getServicoId() {
		return servicoId;
	}
	public void setServicoId(Long servicoId) {
		this.servicoId = servicoId;
	}
	public Integer getContadorBrinde() {
		return contadorBrinde;
	}
	public void setContadorBrinde(Integer contadorBrinde) {
		this.contadorBrinde = contadorBrinde;
	}
	public String getDataBrindeAniversario() {
		return dataBrindeAniversario;
	}
	public void setDataBrindeAniversario(String dataBrindeAniversario) {
		this.dataBrindeAniversario = dataBrindeAniversario;
	}
	public Long getBrindeConfigId() {
		return brindeConfigId;
	}
	public void setBrindeConfigId(Long brindeConfigId) {
		this.brindeConfigId = brindeConfigId;
	}
	
}
