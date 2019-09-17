package br.com.integrado.api.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class PkAgenda{
	private Integer idFuncionario;
	private Date dataAgenda;
	
	public PkAgenda() {
	}
	
	public PkAgenda(Integer idFuncionario, Date dataAgenda) {
		super();
		this.idFuncionario = idFuncionario;
		this.dataAgenda = dataAgenda;
	}

	@Column(name = "id_funcionario")
	public Integer getIdFuncionario() {
		return idFuncionario;
	}
	public void setIdFuncionario(Integer idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	@Column(name = "data_agenda")
	@Temporal(TemporalType.DATE)
	public Date getDataAgenda() {
		return dataAgenda;
	}
	public void setDataAgenda(Date dataAgenda) {
		this.dataAgenda = dataAgenda;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataAgenda == null) ? 0 : dataAgenda.hashCode());
		result = prime * result + ((idFuncionario == null) ? 0 : idFuncionario.hashCode());
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
		PkAgenda other = (PkAgenda) obj;
		if (dataAgenda == null) {
			if (other.dataAgenda != null)
				return false;
		} else if (!dataAgenda.equals(other.dataAgenda))
			return false;
		if (idFuncionario == null) {
			if (other.idFuncionario != null)
				return false;
		} else if (!idFuncionario.equals(other.idFuncionario))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PkAgenda [idFuncionario=" + idFuncionario + ", dataAgenda=" + dataAgenda + "]";
	}
	
}
