package br.com.integrado.api.dtos;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class UFDTO {
	private Integer id;
	@NotEmpty(message = "A Descrição não pode ser vázia")
	private String descUF;
	@Length(min = 2, max = 2, message = "Sigla informada inválida")
	private String siglaUF;
	
	public UFDTO(Integer id, String descUF, String siglaUF) {
		this.id = id;
		this.descUF = descUF;
		this.siglaUF = siglaUF;
	}
	
	public UFDTO(String descUF, String siglaUF) {
		super();
		this.descUF = descUF;
		this.siglaUF = siglaUF;
	}
	
	public UFDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescUF() {
		return descUF;
	}

	public void setDescUF(String descUF) {
		this.descUF = descUF;
	}

	public String getSiglaUF() {
		return siglaUF;
	}

	public void setSiglaUF(String siglaUF) {
		this.siglaUF = siglaUF;
	}
	
}
