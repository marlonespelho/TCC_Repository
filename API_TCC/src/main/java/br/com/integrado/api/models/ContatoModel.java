package br.com.integrado.api.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "contato")
public class ContatoModel implements Serializable{

	private static final long serialVersionUID = -5114938942892179429L;
	
	private Long id;
	private String numTelefone;
	private String email;
	private PessoaModel pessoa;
	
	public ContatoModel() {
	}
	
	public ContatoModel(String numTelefone, String email, PessoaModel pessoa) {
		this.numTelefone = numTelefone;
		this.email = email;
		this.pessoa = pessoa;
	}
	
	public ContatoModel(Long id, String numTelefone, String email, PessoaModel pessoa) {
		this.id = id;
		this.numTelefone = numTelefone;
		this.email = email;
		this.pessoa = pessoa;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "num_telefone", nullable = false)
	public String getNumTelefone() {
		return numTelefone;
	}
	public void setNumTelefone(String numTelefone) {
		this.numTelefone = numTelefone;
	}
	
	@Column(nullable = false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@ManyToOne
	@JoinColumn(name = "id_pessoa")
	public PessoaModel getPessoa() {
		return pessoa;
	}
	public void setPessoa(PessoaModel pessoa) {
		this.pessoa = pessoa;
	}
	@Override
	public String toString() {
		return "ContatoModel [id=" + id + ", numTelefone=" + numTelefone + ", email=" + email + ", pessoa=" + pessoa
				+ "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContatoModel other = (ContatoModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
