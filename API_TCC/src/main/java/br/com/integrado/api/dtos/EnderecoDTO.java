package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class EnderecoDTO {
	private Long id;
	private Long pessoaId;
	private Integer cidadeId;
	@NotEmpty(message = "Logradouro não pode ser vazio")
	private String logradouro;
	@NotEmpty(message = "Número não pode ser vazio")
	private String numero;
	@NotEmpty(message = "Bairro não pode ser vazio")
	private String bairro;
	@NotEmpty(message = "CEP não pode ser vazio")
	@Length(max = 8, min = 8, message = "cep inválido")
	private String cep;
	private String complemento;
	
	public EnderecoDTO() {
		
	}
	
	public EnderecoDTO(Long pessoaId, Integer cidadeId,String logradouro,
			String numero, String bairro, String cep, String complemento) {
		this.pessoaId = pessoaId;
		this.cidadeId = cidadeId;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.complemento = complemento;
	}
	
	public EnderecoDTO(Long id, Long pessoaId, Integer cidadeId,String logradouro,
			String numero, String bairro, String cep, String complemento) {
		this.id = id;
		this.pessoaId = pessoaId;
		this.cidadeId = cidadeId;
		this.logradouro = logradouro;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.complemento = complemento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getPessoaId() {
		return pessoaId;
	}
	public void setPessoaId(Long pessoaId) {
		this.pessoaId = pessoaId;
	}
	public Integer getCidadeId() {
		return cidadeId;
	}
	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}
	public String getLogradouro() {
		return logradouro;
	}
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	
}
