package br.com.integrado.api.entities;

import javax.persistence.Embeddable;


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
