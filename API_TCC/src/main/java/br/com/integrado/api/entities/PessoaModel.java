package br.com.integrado.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public class PessoaModel implements Serializable{

	private static final long serialVersionUID = -6660444088009853730L;
	
	private Long id;
	private String nome;
	private Boolean inAtivo;
	
	public PessoaModel() {

	}
	
	public PessoaModel(String nome, Boolean inAtivo) {
		this.nome = nome;
		this.inAtivo = inAtivo;
	}

	public PessoaModel(Long id, String nome, Boolean inAtivo) {
		this.id = id;
		this.nome = nome;
		this.inAtivo = inAtivo;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Column(name = "in_ativo", nullable = false)
	public Boolean getInAtivo() {
		return inAtivo;
	}
	public void setInAtivo(Boolean inAtivo) {
		this.inAtivo = inAtivo;
	}
	
	@Override
	public String toString() {
		return "PessoaModel [id=" + id + ", nome=" + nome + ", inAtivo=" + inAtivo + "]";
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
		PessoaModel other = (PessoaModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
