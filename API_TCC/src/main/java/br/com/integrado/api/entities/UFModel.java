package br.com.integrado.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="uf")
public class UFModel implements Serializable{

	private static final long serialVersionUID = -9105677290646015884L;

	private Integer id;
	private String descUF;
	private String siglaUF;
	
	public UFModel() {
		super();
	}
	
	public UFModel(String descUF, String siglaUF) {
		this.descUF = descUF;
		this.siglaUF = siglaUF;
	}

	public UFModel(Integer id, String descUF, String siglaUF) {
		this.id = id;
		this.descUF = descUF;
		this.siglaUF = siglaUF;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "desc_uf", nullable = false)
	public String getDescUF() {
		return descUF;
	}
	public void setDescUF(String descUF) {
		this.descUF = descUF;
	}
	
	@Column(name = "sigla_uf", nullable = false, unique = true)
	public String getSiglaUF() {
		return siglaUF;
	}
	public void setSiglaUF(String siglaUF) {
		this.siglaUF = siglaUF;
	}
	
	@Override
	public String toString() {
		return "UFModel [id=" + id + ", descUF=" + descUF + ", siglaUF=" + siglaUF + "]";
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
		UFModel other = (UFModel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
