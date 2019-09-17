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
@Table(name = "cidade")
public class CidadeModel implements Serializable{

	private static final long serialVersionUID = -117794055000134193L;
	
	private Integer id;
	private String nome;
	private UFModel UF;
	
	public CidadeModel() {
	}

	public CidadeModel(String nome, UFModel uF) {
		this.nome = nome;
		UF = uF;
	}

	public CidadeModel(Integer id, String nome, UFModel uF) {
		this.id = id;
		this.nome = nome;
		UF = uF;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	@ManyToOne
	@JoinColumn(name = "id_uf")
	public UFModel getUF() {
		return UF;
	}
	public void setUF(UFModel uF) {
		UF = uF;
	}

	@Override
	public String toString() {
		return "CidadeModel [id=" + id + ", nome=" + nome + ", UF=" + UF + "]";
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
		CidadeModel other = (CidadeModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
		
}
