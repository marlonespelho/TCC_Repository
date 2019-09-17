package br.com.integrado.api.models;

import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.br.CPF;

@Embeddable
public class CPFModel {
	
	private String CPF;

	public CPFModel() {
		
	}
	
	public CPFModel(String cPF) {
		super();
		CPF = cPF;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}	
	
}
